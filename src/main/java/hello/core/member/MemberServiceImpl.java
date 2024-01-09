package hello.core.member;

public class MemberServiceImpl implements MemberService {
    // 이클래스는 인터페이스인 MemberRepository에 의존하지만 동시에 구현체인 MemoryMemberRepository에도 의존적이다.
    // 올바른 방향은 아님 -> DIP 위반. 변경시 문제가 됨

    //MemberRepository는 인터페이스이므로 구현체로 객체화
    //private final MemberRepository memberRepository = new MemoryMemberRepository();

    private final MemberRepository memberRepository;


    // 생성자를 통해 객체가 주입  == 생성자 주입
    public MemberServiceImpl(MemberRepository memberRepository) {
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
