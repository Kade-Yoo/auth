package com.example.application.login.controller

import com.example.application.config.jwt.Jwt
import com.example.application.login.dto.LoginRequest
import com.example.application.login.usecase.LoginUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * 로그인 관련 API를 관리하는 Controller
 */
@RestController
class LoginController(
    private val loginUsecase: LoginUsecase
) {
    /**
     * 로그아웃 성공 후 Get Method 오류 방지를 위한 로그인
     */
    @GetMapping("/login")
    fun loginPage() : String = "Test"

    /**
     * 로그인 API
     *
     * @param request 로그인 정보
     */
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest) : ResponseEntity<Jwt> = ResponseEntity.ok(loginUsecase.login(request))

    @GetMapping("/")
    fun main() = "Ldap Testing"
//
//    /**
//     * 토큰 갱신 API
//     *
//     * @param request 로그인 정보
//     */
//    @PostMapping("/expand-token")
//    fun expendToken(@RequestHeader("Authorization_Refresh") refreshToken: String?, @RequestBody request: LoginRequest) : ResponseEntity<Jwt> =
//        ResponseEntity.ok(loginUsecase.expendToken(refreshToken, request))
}