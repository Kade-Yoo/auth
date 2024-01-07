package com.example.application.config.jwt

import com.example.application.config.constants.Constants.Companion.AUTH_CLAIM_NAME
import com.example.application.config.constants.Constants.Companion.BEARER_BY_TOKEN_PREFIX
import com.example.domain.common.ErrorCode
import com.example.domain.common.UnauthorizedException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import java.security.Key
import java.util.*
import java.util.stream.Collectors
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class JwtProvider(@Value("\${jwt.secret-key}") secretKey: String) {

    private val log = LoggerFactory.getLogger(this.javaClass)
    private lateinit var key: Key

    init {
        val keyBytes: ByteArray = Decoders.BASE64.decode(secretKey)
        key = Keys.hmacShaKeyFor(keyBytes)
    }

    /**
     * accessToken, refreshToken 생성
     *
     * @param authentication 인증 정보
     */
    fun generateToken(authentication: Authentication): Jwt {
        val authorities: String = authentication.authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","))

        val now: Long = Date().time
        val accessTokenExpiresIn = Date(now + 60 * 1000L)
        val accessToken: String = Jwts.builder()
            .setSubject(authentication.name)
            .claim(AUTH_CLAIM_NAME, authorities)
            .setExpiration(accessTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        val refreshToken: String = Jwts.builder()
            .setExpiration(Date(now + 86400000))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()

        return Jwt(BEARER_BY_TOKEN_PREFIX, accessToken, refreshToken)
    }

    /**
     * Jwt 인증 정보 조회
     *
     * @param token 토큰
     * @return 인증 정보
     */
    fun getAuthentication(token: String?): Authentication {
        val claims = parseClaims(token)
        if (claims[AUTH_CLAIM_NAME] == null) {
            throw UnauthorizedException(ErrorCode.UNAUTHORIZED_ERROR)
        }

        val authorities: Collection<GrantedAuthority?> =
            Arrays.stream(claims[AUTH_CLAIM_NAME].toString().split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                .map(::SimpleGrantedAuthority)
                .collect(Collectors.toList())

        val principal: UserDetails = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    /**
     * 토큰 정보 검증
     *
     * @param token 토큰
     * @return 토큰 검증 여부
     */
    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            log.info("Invalid JWT", e)
        } catch (e: MalformedJwtException) {
            log.info("Invalid JWT", e)
        } catch (e: ExpiredJwtException) {
            log.info("Expired JWT", e)
        } catch (e: UnsupportedJwtException) {
            log.info("Unsupported JWT Token", e)
        } catch (e: IllegalArgumentException) {
            log.info("JWT claims string is empty.", e)
        }
        return false
    }

    /**
     * Jwt AccessToken 만료 여부
     *
     * @param token 토큰
     * @return 토큰 만료 여부
     */
    fun isExpired(token: String?): Boolean {
        val claims = parseClaims(token)
        val expiration = claims.expiration ?: throw UnauthorizedException(ErrorCode.UNAUTHORIZED_ERROR)
        return Date() > expiration
    }

    /**
     * Jwt Refresh Token 여부
     *
     * @param token 토큰
     * @return 토큰 만료 여부
     */
    fun isRefreshToken(refreshToken: String, key: Key): Boolean {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .isSigned(refreshToken)
    }

    /**
     * accessToken 복호화
     *
     * @param token 토큰
     */
    private fun parseClaims(token: String?): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            throw UnauthorizedException(ErrorCode.UNAUTHORIZED_ERROR)
        }
    }
}