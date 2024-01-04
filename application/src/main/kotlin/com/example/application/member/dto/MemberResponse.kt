package com.example.application.member.dto

import com.example.domain.member.dto.MemberResult
import com.fasterxml.jackson.annotation.JsonIgnore

data class MemberResponse(
    val email: String,
    @JsonIgnore
    val password: String,
    val role: String,
) {
    companion object {
        fun of(memberResult: MemberResult) = MemberResponse(memberResult.email, memberResult.password, memberResult.role)
    }
}