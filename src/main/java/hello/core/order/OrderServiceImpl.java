package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 추상 인터페이스 뿐만 아니라 구현체에도 의존하고 있어 DIP 위반이다.
    // 정액할인 정책을 정률할인 정책으로 바꾸는 순간 Impl의 코드를 수정해야 하기 때문에 OCP 위반이다.
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    // 구현체가 아니라 추상화에만 의존하도록 변경한다. 하지만 NullPointerException.

    private final MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        // 단일책임원칙이 잘 지켜짐
        // 할인정책이 바뀌면 DiscountPolicy만 고치면 되니까.
        // 잘 되지 않았으면 이 구현체에서 다 바꿔야 하니까 그렇다.
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
