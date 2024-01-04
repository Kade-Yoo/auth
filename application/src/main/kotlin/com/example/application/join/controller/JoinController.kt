package com.example.application.join.controller

import com.example.application.join.dto.JoinRequest
import com.example.application.join.usecase.JoinUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/join")
class JoinController(
    private val joinUsecase: JoinUsecase
) {
    /**
     * 회원 가입
     *
     * @param request 가입 정보
     */
    @PostMapping
    fun join(@RequestBody request: JoinRequest): ResponseEntity<Void> {
        joinUsecase.join(request)
        return ResponseEntity.ok().build()
    }

    /**
     * 회원 정보 수정
     *
     * @param request 가입 정보
     */
    @PutMapping
    fun modify(@RequestBody request: JoinRequest): ResponseEntity<Void> {
        joinUsecase.modify(request)
        return ResponseEntity.ok().build()
    }

    /**
     * 회원 탈퇴
     *
     * @param request 가입 정보
     */
    @DeleteMapping
    fun withdraw(@RequestBody request: JoinRequest): ResponseEntity<Void> {
        joinUsecase.withdraw(request)
        return ResponseEntity.ok().build()
    }
}