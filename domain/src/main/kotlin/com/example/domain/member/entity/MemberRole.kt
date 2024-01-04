package com.example.domain.member.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Entity
class MemberRole(
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    var member: Member,

    @Column
    val role: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int unsigned", nullable = false)
    var memberRoleId: Long? = null

    @Override
    fun getAuthorities(): GrantedAuthority {
        return SimpleGrantedAuthority(
            role
        )
    }

    companion object {
        fun create(member: Member, role: String): MemberRole = MemberRole(member, role)
    }
}