package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
// @RequiredArgsConstructor : final을 받는 생성자를 기본으로 만들어준다.
// @RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 추상 인터페이스 뿐만 아니라 구현체에도 의존하고 있어 DIP 위반이다.
    // 정액할인 정책을 정률할인 정책으로 바꾸는 순간 Impl의 코드를 수정해야 하기 때문에 OCP 위반이다.
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    // 구현체가 아니라 추상화에만 의존하도록 변경한다. 하지만 NullPointerException.
    // final 키워드는 불변, 필수 객체에 지정하면 된다.(생성자 주입 시에는 가능하고, 수정자 주입 시에는 에러)

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // 3. 필드 주입방식(안티패턴으로 사장되는 추세)
    // @Autowired private MemberRepository memberRepository;
    // @Autowired private DiscountPolicy discountPolicy;

    // 1. 생성자가 하나만 있으면 Autowired를 생략해도 주입된다.
    // @RequiredArgsConstructor가 만들어준 생성자

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }


    // 2. 수정자 주입 방식
    // 선택, 변경 가능성이 있는 의존관계에 사용, final 붙이면 컴파일 에러남.
/*
    @Autowired(required = false) // 선택적으로 하고 싶을 때 옵션
    public void setMemberRepository(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    @Autowired
    public void  setDiscountPolicy(DiscountPolicy discountPolicy){
        this.discountPolicy = discountPolicy;
    }
*/

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
