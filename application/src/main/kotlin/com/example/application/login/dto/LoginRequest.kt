package com.example.application.login.dto

/**
 * 로그인 요청 dto
 */
data class LoginRequest(
    val email: String,
    val password: String,
)