package com.example.application.member.controller

import com.example.application.member.dto.MemberResponse
import com.example.application.member.usecase.MemberUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 회원 관련 API
 */
@RestController
@RequestMapping("/member")
class MemberController(
    private val memberUsecase: MemberUsecase
) {
    /**
     * 전체 회원 정보 조회
     *
     * @return 회원 정보
     */
    @GetMapping("/list")
    fun getMembers() : ResponseEntity<List<MemberResponse>> =
        ResponseEntity.ok(memberUsecase.getMembers())

    /**
     * 선택한 회원 정보 조회
     *
     * @param id 회원 id
     * @return 회원 정보
     */
    @GetMapping
    fun getMember(@RequestParam("id") id: Long) : ResponseEntity<MemberResponse> =
        ResponseEntity.ok(memberUsecase.getMember(id))
}