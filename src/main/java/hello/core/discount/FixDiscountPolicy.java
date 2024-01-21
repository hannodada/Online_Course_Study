package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
// Error creating bean with name 'orderServiceImpl' defined
// in file [D:\Online_Course_Study\out\production\classes\hello\core\order\OrderServiceImpl.class]:
// Unsatisfied dependency expressed through constructor parameter 1:
// No qualifying bean of type 'hello.core.discount.DiscountPolicy'
// available: expected single matching bean but found 2: fixDiscountPolicy,rateDiscountPolicy

// 하위 타입으로 선언하여 해결할 수 도 있고 수동 등록도 가능하긴 하지만
// 의존 관계 자동 주입에서 해결이 가능하다.
// 1. @Autowired 필드명 매칭
// 2. @Quilifier -> @Quilifier끼리 메칭 -> 빈이름매칭
// 3. @Primary 사용
public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP){
            return discountFixAmount;
        } else{
            return 0;
        }
    }
}
