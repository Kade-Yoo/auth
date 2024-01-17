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
) {

    /**
     * username을 통해 User상세 정보를 가져온다
     *
     * username이 없을 경우 UsernameNotFoundException이 발생한다
     * ?? 근데 implements를 했는데 throw Exception이 왜 안붙지?????????!!!
     */
    fun loadUserByUsername(username: String): UserDetails {
        val memberResult = memberService.getMemberByEmail(username)
        return User.builder()
            .username(memberResult.email)
            .password(passwordEncoder.encode(memberResult.password))
            .roles(memberResult.role)
            .build()
    }
}