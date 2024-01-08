package com.example.application.join.usecase

import com.example.application.join.dto.JoinRequest
import com.example.domain.member.service.MemberService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 회원가입 관련 기능
 */
@Service
class JoinUsecase(
    private val memberService: MemberService
) {
    /**
     * 회원 가입
     */
    @Transactional(readOnly = false)
    fun join(joinRequest: JoinRequest) {
        memberService.join(command = joinRequest.of())
    }

    /**
     * 회원 정보 수정
     */
    @Transactional(readOnly = false)
    fun modify(joinRequest: JoinRequest) {
        memberService.modify(command = joinRequest.of())
    }

    /**
     * 회원 탈퇴
     */
    @Transactional(readOnly = false)
    fun withdraw(joinRequest: JoinRequest) {
        memberService.withdraw(command = joinRequest.of())
    }
}