package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestComponent;
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

        // ThreadA : A 사용자가 10000원 주문.
        int userAPrice = statefulService1.order("userA", 10000);
        // ThreadB : B 사용자가 10000원 주문.
        int userBPrice = statefulService2.order("userB", 20000);

        // ThreadA : 사용자A가 주문 금액 조회
        //int price = statefulService1.getPrice();
        System.out.println(userAPrice);

        //Assertions.assertThat(statefulService2.getPrice()).isEqualTo(2000);

        // 싱글톤으로 인해 실제 불려지는건 같은 statefulService1, statefulService2로 나뉘더라도
        // 한 객체 이므로 결국 같은 price의 값을 변경하게 된다.

        // 따라서 스프링 빈은 공유필드를 사용하지 않는 무상태로 설계해야한다.
        // 공유 되지 않는 지역 변수나 파라미터를 사용할 것.
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}