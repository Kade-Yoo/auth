package com.example.domain.member.dto

import com.example.domain.member.entity.Member

data class MemberResult(
    var id: Long?,
    var email: String,
    var password: String,
    var role: String,
) {
    constructor(member: Member) : this(null, member.email, member.password, member.memberRole.role) {
        this.id = member.memberId
        this.email = member.email
        this.password = member.password
        this.role = member.memberRole.role
    }
}