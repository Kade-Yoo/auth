package com.example.application.join.dto

import com.example.domain.member.dto.JoinCommand

data class JoinRequest(
    val email: String,
    val password: String,
    val role: String?
) {
    fun of() = JoinCommand(email, password, role)
}