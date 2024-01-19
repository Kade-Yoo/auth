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
    - UserDetailsService -> JdbcUserDetailsManager(DB로 User정보 관리), LdapUserDetailsManager를 통해 구현될 수 있다 
      - Security책을 보며 연습하기 위해 CustomUserDetailsService를 제거하고 UserDetailsService 빈 등록을 해주었는데 왜안되는걸까?
        - UserDetailsService 빈 등록한 로직에 User를 등록하는 내용이 있어 해당 User로 인증을 받고 로그인하게 되면 정상 동작한다.
      - UserDetails(loadUserByUsername 메소드를 통해 반환됨)
        - GrantedAuthority
            - SimpleGrantedAuthority : User의 roles을 지정해줄 때 사용하는 클래스
            - SwitchUserGrantedAuthority : 인증 성공한 사용자와 변경을 하기 위한 클래스
      - JdbcUserDetailsManager
        - Table과 Column을 Custom하게 사용하기 위해선 usersByUsernameQuery, authoritiesByUsernameQuery를 수정해서 사용해야 하며,
        - 기존 정의되어 있는 쿼리대로 컬럼 위치와 갯수를 맞춰줘야 재정의한 쿼리로 정상 동작한다
        - 근데 다시 보니 protected로 되어 있어서 override해서 사용하면 Table,Column도 내가 원하는대로 변경해서 사용할 수 있을 것 같다.
        - 기존 정의되어 있는 로직에 종속적이게 개발할 수 밖에 없어보여 Custom클래스를 새로 만들어서 사용하는게 편해보인다.
        - **JdbcUserDetailsManager를 상속받아서 dataSource셋팅해주고 사용하면 Custom하게 사용할 수 있다**
        ```java
            protected List<UserDetails> loadUsersByUsername(String username) {
		        return getJdbcTemplate().query(getUsersByUsernameQuery(), this::mapToUser, username);
            }
        ```
      - LdapUserDetailsManager
        - Ldap서버와 통신을 통해 Ldap서버에 저장되어 있는 데이터를 이용해 인증 처리를 하는 방식
        - Ldap인증 방식은 AuthenticationFilter -> ProviderManager -> AbstractLdapAuthenticationProvider -> LdapUserDetailsMapper 순서로 동작한다.
        - UserDetailsService를 통해 UserDetails를 가져오는것이 아닌 LdapUserDetailsMapper를 통해 가져온다.
        - 그리고 Authority정보는 LdapAuthoritiesPopulator를 통해서 가져온다.
        - Ldap Security를 Custom하게 만들기 위해선 Manager, Provider, Mapper, Populator 클래스를 상속받아 메소드를 재정의해야 할 것 같다.
    - CustaomAuthenticationProvider
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