package com.example.application.member.usecase

import com.example.application.member.dto.MemberResponse
import com.example.domain.member.service.MemberService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberUsecase(
    private val memberService: MemberService
) {
    fun getMembers() : List<MemberResponse> {
        val members = memberService.getMembers()
        return members.stream().map { MemberResponse.of(it) }.toList()
    }
    fun getMember(id: Long) = MemberResponse.of(memberService.getMemberById(id))
}