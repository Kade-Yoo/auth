package com.example.application.config.security

import com.example.domain.member.service.MemberService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val memberService: MemberService,
    private val passwordEncoder: PasswordEncoder,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val memberResult = memberService.getMemberByEmail(username)
        return User.builder()
            .username(memberResult.email)
            .password(passwordEncoder.encode(memberResult.password))
            .roles(memberResult.role)
            .build()
    }
}