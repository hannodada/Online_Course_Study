package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // basePackages,basePackageClasses 의 default는 @ComponentScan이 붙은 클래스의 패키지 부터 시작
        // 따라서 설정 정보 클래스를 프로젝트 최상단에 보통 두는 것을 권장
        // ex) 프로젝트 시작 루트인 com.hello 아래 생성
        basePackages = "hello.core", // 스캔 시작 점을 지정 라이브러리가 많으면 유용
        basePackageClasses = AutoAppConfig.class, // 클래스 위치를 지정
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
/*

@ComponentScan
- @Component Class를 찾아서 자동으로 스프링 Bean으로 등록 해줌
- Configuration Class도 등록 함 -> Configuration Class에 @Component 어노테이션이 붙어 있음
- excludeFilters : 그중 뺄 것을 정함 , 여기서는 기존 실습한 AppConfig를 제외하기 위해 사용한 것으로 일반 적인 것이 아니다.
- 기본적으로 스캔하는 어노테이션 : @Component, @Controller, @Service, @Repository, @Configuration
*/
public class AutoAppConfig {


}
