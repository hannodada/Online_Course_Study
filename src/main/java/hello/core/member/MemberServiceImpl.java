package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {
    // 이클래스는 인터페이스인 MemberRepository에 의존하지만 동시에 구현체인 MemoryMemberRepository에도 의존적이다.
    // 올바른 방향은 아님 -> DIP 위반. 변경시 문제가 됨

    //MemberRepository는 인터페이스이므로 구현체로 객체화
    //private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;


    @Autowired
    // 의존관계 주입을 해주는 것.
    // AppConfig에서는 @Bean 으로 직접 설정정보를 작성하고 의존 관계도 명시
    // 하지만 @ComponentScan을 사용하면 자동으로 빈으로 등록되기 때문에 수동으로 의존 관계를 주입해줄 수가 없다.
    // 따라서 @Autowired를 통해 의존 관계를 주입해 주는 것
    // == (ac.getBean(MemberRepository.class)) + 추가 기능
    public MemberServiceImpl(MemberRepository memberRepository)        {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
