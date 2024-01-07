# 과제

## 환경
- Spring Boot 3.2.1
- Kotlin 1.9.21
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