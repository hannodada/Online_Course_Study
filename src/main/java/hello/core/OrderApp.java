package hello.core;

import hello.core.discount.Order;
import hello.core.discount.OrderService;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {

//        AppConfig appConfig = new AppConfig();
//
//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();


        /*ApplicationContext == 스프링 컨테이너
        *
        * 스프링 컨테이너는 @configuration이 붙은 ApppConfig를 설정 정보로 사용
        * @Bean 이 붙은 메서드 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록
        * 이렇게 스프링 컨테이너에 등록된 객체를 스프링 빈이라고 한다.
        * */
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);
        System.out.println("calculatePrice = " + order.calculatePrice());

    }
}
