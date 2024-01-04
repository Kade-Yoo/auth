package com.example.domain.member.repository

import com.example.domain.member.entity.Member
import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {

    fun findByEmail(email: String): Optional<Member>
    fun deleteByEmail(email: String)
}