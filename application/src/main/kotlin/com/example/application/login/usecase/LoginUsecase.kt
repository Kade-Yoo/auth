package com.example.application.login.usecase

import com.example.domain.common.ErrorCode
import com.example.domain.common.UnauthorizedException
import com.example.application.config.jwt.Jwt
import com.example.application.config.jwt.JwtProvider
import com.example.application.login.dto.LoginRequest
import com.example.domain.common.InvalidInputException
import com.example.domain.member.dto.MemberResult
import com.example.domain.member.service.MemberService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class LoginUsecase(
    private val memberService: MemberService,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider,
) {
    /**
     * 로그인
     *
     * 1. email, password 검증
     * 2. email, password로 Authentication 객체 생성
     * 3. JWT 생성
     */
    fun login(request: LoginRequest): Jwt {
        val memberResult: MemberResult = memberService.getMemberByEmail(request.email)
        if (!passwordEncoder.matches(request.password, memberResult.password)) {
            throw InvalidInputException(ErrorCode.UNAUTHORIZED_ERROR)
        }

        val authenticationToken = UsernamePasswordAuthenticationToken(request.email, memberResult.password)
        val authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)
        return jwtProvider.generateToken(authentication)
    }

    /**
     * 로그인
     *
     * 1. email, password 검증
     * 2. email, password로 Authentication 객체 생성
     * 3. JWT 생성
     */
    fun expendToken(request: LoginRequest): Jwt {
        val memberResult: MemberResult = memberService.getMemberByEmail(request.email)
        if (!passwordEncoder.matches(request.password, memberResult.password)) {
            throw InvalidInputException(ErrorCode.UNAUTHORIZED_ERROR)
        }

        val authenticationToken = UsernamePasswordAuthenticationToken(request.email, memberResult.password)
        val authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)
        return jwtProvider.generateToken(authentication)
    }
}