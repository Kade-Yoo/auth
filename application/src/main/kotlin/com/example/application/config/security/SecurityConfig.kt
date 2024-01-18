package com.example.application.config.security

import com.example.application.config.jwt.JwtAuthenticationFilter
import com.example.application.config.jwt.JwtProvider
import javax.sql.DataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtProvider: JwtProvider,
//    private val customAuthenticationProvider: CustomAuthenticationProvider,
    private val dataSource: DataSource
) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain? {
        return httpSecurity
            // http basic 인증을 하지 않고 커스텀한 인증 절차를 거치기 때문에 disable
            .httpBasic { it.disable() }
            // Rest API는 Stateless(서버에 정보를 저장하지 않는) 하기 때문에 크로스사이트 변조를 disable처리
            .csrf { it.disable() }
            // session 사용하지 않도록 설정
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers("/h2-console/**","/favicon.ico", "/login", "/join", "/logout").permitAll()
                    .requestMatchers("/member").hasAnyRole("ADMIN", "USER")
                    .requestMatchers("/member/list").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }
            // h2-console을 사용해야 되기 때문에 sameOrigin 설정
            // sameOrigin은 같은 oigin일 때 iframe 태그 허용
            .headers { it -> it.frameOptions { it.sameOrigin() } }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
            // Custom Provider 등록
//            .authenticationProvider(customAuthenticationProvider)
            .build()
    }
}