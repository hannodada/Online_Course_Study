# Week03 240121
## 정적코드분석
- 사용 툴 : SonarQube 
- 표준 컨벤션
- NULL Check
- Programming Mistake Detector, 다음과 같은 소스코드의 결함을 탐지
  - try, catch, finally, switch의 빈 블럭 같은 것
  - 사용하지 않는 변수, 파라미터, 메소드
  - 불필요한 조건문, 루프에 루프가 더해지는 경우
  - String, StringBuffer의 낭비
  - 높은 복잡도
  - 중복 코드


## Null Check
### 1. 일반적인 null 체크:

```java
if (variable != null) {
    // 변수가 null이 아닌 경우 실행할 코드
}
```

- 변수가 null인지 확인하고, null이 아닐 때 실행할 코드를 지정
  
- 가장 기본적이고 직관적인 방법

- 임의의 객체나 배열 등에 대한 null 여부를 확인할 때 주로 사용


### 2. Optional 사용:

```java
Optional<String> optionalValue = Optional.ofNullable(variable);
if (optionalValue.isPresent()) {
    // 변수가 null이 아닌 경우 실행할 코드
}
```

- `Optional` 클래스를 사용하여 null 여부를 확인
- `ofNullable` 메서드로 생성한 `Optional` 객체를 사용하여 null이 아닌 경우에 실행할 코드를 지정

- 자바 8 이상에서 사용 가능하며, 메서드의 반환 값 등에 null 대신 `Optional`을 사용하여 명시적으로 표현하고자 할 때 사용

- 메서드에서 값이 없을 수 있는 경우에 사용
- 

### 3. Objects.requireNonNull 사용:

```java
Objects.requireNonNull(variable, "변수는 null이 될 수 없습니다.");
// 변수가 null이 아닌 경우 실행할 코드
```

-  `Objects.requireNonNull` 메서드를 사용하여 변수가 null이 아닌지 확인
-  null인 경우 지정한 에러 메시지를 갖는 `NullPointerException`이 발생

- 메서드의 파라미터로 전달되는 값이 null이 아니어야 하는 경우에 사용

- 메서드가 실행되기 전에 특정 변수의 null 여부를 강제로 확인하고자 할 때 사용

- 에러 메시지를 명시적으로 전달할 수 있어서 디버깅이 용이


ex) 예시 코드

```
StepSetting stepSetting = getStepSetting(step_settings, step_seq);
Boolean useRejectRestrict = stepSetting.getOption().getUse_reject_restrict();
```
1. 일반적인 null 체크:
```
StepSetting stepSetting = getStepSetting(step_settings, step_seq);
Boolean useRejectRestrict = null;

if (stepSetting != null && stepSetting.getOption() != null) {
    useRejectRestrict = stepSetting.getOption().getUse_reject_restrict();
}

```

2. Optional 사용:
```
StepSetting stepSetting = getStepSetting(step_settings, step_seq);
Boolean useRejectRestrict = Optional.ofNullable(stepSetting)
                                      .map(ss -> ss.getOption().getUse_reject_restrict())
                                      .orElse(/*기본값 or Exception*/);

```

3. Objects.requireNonNull 사용:
```
StepSetting stepSetting = getStepSetting(step_settings, step_seq);
Objects.requireNonNull(stepSetting, "stepSetting은 null일 수 없습니다.");
Boolean useRejectRestrict = stepSetting.getOption().getUse_reject_restrict();

```
