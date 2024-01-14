package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);
        
        // 쓰레드A : A사용자가 10000원 주문
        int userAPrice = statefulService1.order("userA", 10000);
        
        // 쓰레드B : B사용자가 20000원 주문
        int userBPrice = statefulService2.order("userB", 20000);
        
        // 쓰레드A : 사용자A 주문 금액 조회
        // int price = statefulService1.getPrice();
        System.out.println("price = " + userAPrice);

        // Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig{

        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}