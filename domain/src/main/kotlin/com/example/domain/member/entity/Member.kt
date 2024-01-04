package com.example.domain.member.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne


@Entity
class Member private constructor(
    @Column(columnDefinition = "varchar", nullable = false)
    var email: String,

    @Column(columnDefinition = "varchar", nullable = false)
    var password: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int unsigned", nullable = false)
    val memberId: Long? = null

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], mappedBy = "member")
    lateinit var memberRole: MemberRole

    fun update(email: String, password: String) {
        this.email = email
        this.password = password
    }

    companion object {
        fun create(
            email: String,
            password: String
        ): Member {
            return Member(
                email,
                password
            )
        }
    }
}