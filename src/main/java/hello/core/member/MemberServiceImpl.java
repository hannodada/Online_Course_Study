package hello.core.member;

public class MemberServiceImpl implements MemberService {
    // DIP를 위반하고 있다.(인터페이스-추상화 의존, 구현체 의존 동시에 하는 형태임.)
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
