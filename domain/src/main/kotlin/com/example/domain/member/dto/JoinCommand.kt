package com.example.domain.member.dto

/**
 * 회원 가입 커맨드 dto
 */
data class JoinCommand(
    val email: String,
    val password: String,
    val role: String?
)