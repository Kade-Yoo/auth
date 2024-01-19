package com.example.application.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.ldap.core.ContextSource
import org.springframework.ldap.core.support.BaseLdapPathContextSource
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper

@Configuration
class LdapSecurityConfig(
    private val contextSource: BaseLdapPathContextSource
) {

    /**
     * Ldap 서버에 접속하기 위한 정보
     *
     * 1. contextSource()
     * 2. user 패턴
     * 3. password
     */
    @Autowired
    @Throws(Exception::class)
    fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .ldapAuthentication()
            .ldapAuthoritiesPopulator(authoritiesPopulator())
            .contextSource(contextSource)
            .userDetailsContextMapper(userDetailsMapper())
            .contextSource()
            // 내장 ldap을 사용하지 않을 때 사용할 ldap 서버 url
            .url("ldap://localhost:8389/dc=springframework,dc=org")
            // dc=springframework,dc=org 형식을 갖고 있음
            .root("dc=springframework,dc=org")
            .port(8389)
            .and()
            // 사용자 DN(Distinguished Name) 패턴, uid = 사용자 아이디, ou = 조직
            .userDnPatterns("uid={0},ou=people")
            .groupSearchBase("ou=groups")
            .passwordCompare()
            // 비밀번호 Encoding 방식
            .passwordEncoder(BCryptPasswordEncoder())
            // 디렉토리의 비밀번호 속성
            .passwordAttribute("userPassword")
    }

    fun authoritiesPopulator() : DefaultLdapAuthoritiesPopulator =
        DefaultLdapAuthoritiesPopulator(contextSource, "ou=groups")

    fun userDetailsMapper() : LdapUserDetailsMapper = LdapUserDetailsMapper()
}