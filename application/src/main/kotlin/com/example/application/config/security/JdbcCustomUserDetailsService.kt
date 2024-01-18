package com.example.application.config.security

import com.example.domain.member.service.MemberService
import javax.sql.DataSource
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.stereotype.Service

/**
 * DB를 사용할 때 이용하는 UserDetailsService
 */
//@Service
class JdbcCustomUserDetailsService(
    private val memberService: MemberService,
    private val passwordEncoder: PasswordEncoder,
    dataSource: DataSource) : JdbcUserDetailsManager(dataSource) {

    /**
     * username을 통해 User상세 정보를 가져온다
     *
     * username이 없을 경우 UsernameNotFoundException이 발생한다
     * ?? 근데 implements를 했는데 throw Exception이 왜 안붙지?????????!!!
     */
    override fun loadUserByUsername(username: String): UserDetails {
        val memberResult = memberService.getMemberByEmail(username)
        return User.builder()
            .username(memberResult.email)
            .password(passwordEncoder.encode(memberResult.password))
            .roles(memberResult.role)
            .build()
    }
}