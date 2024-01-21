package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // basePackages = "hello.core.member",
        // basePackageClasses = AutoAppConfig.class, // 지정하지 않으면 @ComponentScan이 붙은 클래스 피키지가 시작위치로 됨. 관례상 최상단에 Config를 놓는게 좋다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
// @ComponentScan은 @Component가 붙은 클래스를 모두 확인해서 빈으로 등록해준다.
public class AutoAppConfig {

/*
    @Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }*/
}
