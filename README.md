# 과제

## 환경
- Spring Boot 3.2.1
- Kotlin 1.9.21
- Micrometer
  - tracing brave 1.2.1
  - zipkin 2.16.3
- spring-boot-actuator 3.2.1
- ORM(JPA)
- 인증(Spring Security + JWT)

## 구성
### Architecture
- Clean Architecture
    - Multiple Module 구성

### 인증
- Spring Security + JWT
    - SecurityConfig(Spring Security 설정)
    - Jwt (토큰 정보)
    - JwtAuthenticationFilter (토큰 인증 필터)
      - Token 만료 여부 추가
    - JwtProvider (토큰 생성, 토큰 정보)
    - UserDetailsService
      - Security책을 보며 연습하기 위해 CustomUserDetailsService를 제거하고 UserDetailsService 빈 등록을 해주었는데 왜안되는걸까?
        - UserDetailsService 빈 등록한 로직에 User를 등록하는 내용이 있어 해당 User로 인증을 받고 로그인하게 되면 정상 동작한다.
    - CustomAuthenticationProvider
      - AuthenticationProvider를 HttpSecurity에 등록할 수 있다 JwtProvider와 어떤 점이 다른지 확인해보면서 구성하는 방법을 찾아보자

### CRUD API
- 인증 로직을 구현하여 CRUD API는 회원과 관련된 API로 작성

| Method | API URI      | 내용                                      |
|--------|--------------|-----------------------------------------|
| POST   | /login       | 로그인 API                                 |
| POST   | /join        | 회원 가입 API                               |
| PUT    | /join        | 회원 정보 수정 API                            |
| DELETE | /join        | 회원 탈퇴 API                               |
| GET    | /member/list | 회원 정보 전체 조회 API("ADMIN" 권한일 떄만 조회 가능)   |
| GET    | /member      | 회원 정보 조회 API("ADMIN", "USER" 일 떄 조회 가능) |

## 개선 필요한 점
- 토큰이 만료되었을 때 refreshToken을 확인하여 accessToken 재발급
  - refreshToken 재발급은 API 새로 만들어서 해주는게 나을 것 같은데 로직은 어떻게 하지?
  - 흠..................
- Test Code 추가 필요
- 다양한 Exception을 Handling할 수 있도록 개선 필요

## Tracing
- Micrometer 
- Actuator
- Zipkin(우선 local docker에 설치 추후 dockerFile 생성하여 프로젝트 실행 시 설치)
  - AsyncReporter를 이용해서 오류가 떨어져도 기존 로직에 영향이 없다