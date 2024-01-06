package hello.core;

import hello.core.discount.*;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
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
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        // return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
