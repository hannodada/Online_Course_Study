package hello.core;

import hello.core.discount.*;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderServiceImpl;
import hello.core.order.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// factory Method를 통해서 등록하는 방식

/* @Configuration을 통해 싱글톤이 보장되는 방법 -> 이 어노테이션을 지우면 @Bean만으로는 싱글톤이 보장되지 않음
@Bean
public MemberRepository memberRepository() {

     if (memoryMemberRepository가 이미 스프링 컨테이너에 등록되어 있으면?) {
     return 스프링 컨테이너에서 찾아서 반환;
     } else { //스프링 컨테이너에 없으면
     기존 로직을 호출해서 MemoryMemberRepository를 생성하고 스프링 컨테이너에 등록
     return 반환
     }
}   ㅁㅋ
*
* */


@Configuration
public class AppConfig {

    /*
    * AppConfig : 애플리케이션의 전체 동작 방식을 구성(Config) 하기 위해,
    *             `구현 객체를 생성`하고 ,`연결` 하는 책임을 가지는 별도의 설정 클래스
    *
    * - 애플리케이션 실제 동작에 필요한 `구현 객체를 생성`
    * - 생성한 인스턴스의 참조를 `생성자를 통해서 주입(연결)`
    *   MemberServiceImpl -> MemoryMemberRepository
    *   OrderServiceImpl -> MemoryMemberRepository, FixDiscountPolicy
    * */

    /* DI (Dependency Injection) : 의존성 주입
     *
     * 클라이언트인 memberServiceImple 입장에서 보면 의존 관계를 마치 외부에서 주입해주는 것과 같음
     * */

    /* 스프링이 자동으로 관리해주는 싱글톤
    *
    * - 싱글톤이라면 MemoryMemberRepository가 한개만 생성되어야 하지만 코드상으로는 아래와 같이 여러개가 생성됨
    *   @Bean memberService -> new MemoryMemberRepository()
    *   @Bean orderService -> new MemoryMemberRepository()
    *
    * - 그렇다면 실제로는?
    *  하나의 객체임
    * */
    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    /*
    * OrderServiceImpl를 수정하는 것과 AppConfig(외부)를 수정하는 것은 다름.
    *
    * OrderServiceImpl는 확실하게 DIP를 준수하게 됨
    * 오직, 인터페이스에만 의존하고 있기 떄문
    * OrderServiceImpl는 생성자를 통해 어떤 구현객체가 주입될지 알수없다.
    * 따라서 OrderServiceImpl의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부 AppConfig를 통해서 결정된다.
    * OrderServiceImpl는 이제부터 의존관계에 대한 고민이 필요없어지고 오직 실행만 제대로 되면 된다.
    * */

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
        //return null;
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        // return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
