package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {
    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 할인 정책을 변경해주기 위해 수정이 필요해짐 -> OCP위반
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    /*
    * 역할과 구현을 구분 -> O
    * 다형성 활용하여 인터페이스와 구현 객체를 분리 -> O
    * OCP, DIP 준수 -> X
    * 클래스 의존 관계 분석
    * 추상 뿐만 아니라 구현 클래스에도 의존 하고 있음
    *   - 추상 DiscountPolicy
    *   - 구체 : RateDiscountPolicy(), FixDiscountPolicy()
    * 사실상 OrderServiceImpl은 구현 클래스인 RateDiscountPolicy, FixDiscountPolicy에도 의존하고 있는것,
    * 즉, 인터페이스에만 의존하지 못하고 구현 클래스에도 의존하고 있으므로 DIP 위반
    * 또한 정책 변경( or 기능확장)을 위해 클라이언트 코드를 직접 변경(영향을 주므로)을 해야하므로 OCP에도 위반
    *
    * 해결 방법 1.
    * - DIP를 위반하지 않도록 인터페이스에만 의존하도록 의존 관계를 변경하면 된다.
    * */

    //당연히 할당을 안되어 있으므로 Exception in thread "main" java.lang.NullPointerException
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    /*해결 방법 2.
    * 누군가 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해주어야한다.
    * AppConfig 생성
    * AppConfig : 애플리케이션의 전체 동작 방식을 구성(Config) 하기 위해,
    *             `구현 객체를 생성`하고 ,`연결` 하는 책임을 가지는 별도의 설정 클래스
    */

    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice,discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
