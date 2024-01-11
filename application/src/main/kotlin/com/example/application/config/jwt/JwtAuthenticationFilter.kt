package com.example.application.config.jwt

import com.example.application.config.constants.Constants.Companion.AUTHORIZATION_HEADER
import com.example.application.config.constants.Constants.Companion.BEARER_BY_TOKEN_PREFIX
import com.example.application.config.constants.Constants.Companion.REFRESH_AUTHORIZATION_HEADER
import com.example.application.config.constants.Constants.Companion.TOKEN_START_INDEX
import com.example.domain.common.AuthorizationExpiredException
import com.example.domain.common.ErrorCode
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import java.util.*
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean

class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider,
) : GenericFilterBean() {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val token: String? = extractTokenByHeader(request as HttpServletRequest)
        if (Objects.nonNull(token) && jwtProvider.validateToken(token!!)) {
            if (jwtProvider.isExpired(token)) {
                throw AuthorizationExpiredException(ErrorCode.AUTHORIZATION_EXPIRED_ERROR)
            }

            val authentication: Authentication = jwtProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }

        chain.doFilter(request, response)
    }

    /**
     * Header에서 토큰 정보 추출
     */
    private fun extractTokenByHeader(request: HttpServletRequest): String? {
        val token = request.getHeader(AUTHORIZATION_HEADER)
        return if (StringUtils.hasText(token) && token.startsWith(BEARER_BY_TOKEN_PREFIX)) {
            token.substring(TOKEN_START_INDEX)
        } else null
    }

    /**
     * Header에서 Refresh 토큰 정보 추출
     */
    private fun extractRefreshTokenByHeader(request: HttpServletRequest): String? {
        val token = request.getHeader(REFRESH_AUTHORIZATION_HEADER)
        return if (StringUtils.hasText(token) && token.startsWith(BEARER_BY_TOKEN_PREFIX)) {
            token.substring(TOKEN_START_INDEX)
        } else null
    }
}