package com.example.application.config.jwt

/**
 * JWT 정보
 */
data class Jwt(
    val grantType:String,
    val accessToken:String,
    val refreshToken:String
)