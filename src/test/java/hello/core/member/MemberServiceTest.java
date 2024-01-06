package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService;
    // 테스트 전 무조건 실행
    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join(){
        // given
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when
        memberService.join(member);
        Member findeMember = memberService.findMember(1L);

        //then
        Assertions.assertThat(member).isEqualTo(findeMember);

    }
}


// 다른 저장소로 변경 할 때 OCP원칙을 잘 준수 할까?
// DIP 를 잘 지키고 있을까?
// 의존 관계가 인터페이스 뿐만 아니라 구현까지 모두 의존하는 문제가 존재