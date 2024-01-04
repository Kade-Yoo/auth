package com.example.domain.member.service

import com.example.domain.common.ErrorCode
import com.example.domain.common.InvalidInputException
import com.example.domain.member.dto.JoinCommand
import com.example.domain.member.dto.MemberResult
import com.example.domain.member.entity.Member
import com.example.domain.member.entity.MemberRole
import com.example.domain.member.repository.MemberRepository
import com.example.domain.member.repository.MemberRoleRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberService(
    private val passwordEncoder: PasswordEncoder,
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
) {
    @Transactional(readOnly = false)
    fun join(command: JoinCommand) {
        existsEmail(command)
        if (command.role == null) {
            throw InvalidInputException(ErrorCode.INVALID_INPUT_VALUE_ERROR)
        }

        val member = Member.create(
            command.email,
            passwordEncoder.encode(command.password)
        )
        memberRepository.save(member)

        val memberRole = MemberRole.create(member, command.role)
        memberRoleRepository.save(memberRole)
    }

    private fun existsEmail(command: JoinCommand) {
        val findByEmail = this.findByEmail(command.email)
        if (findByEmail.isPresent) {
            throw InvalidInputException(ErrorCode.INVALID_INPUT_VALUE_ERROR)
        }
    }

    @Transactional(readOnly = false)
    fun modify(command: JoinCommand) {
        val member = this.findByEmail(command.email).orElseThrow { InvalidInputException(ErrorCode.INVALID_INPUT_VALUE_ERROR) }
        member.update(command.email, passwordEncoder.encode(command.password))
    }

    @Transactional(readOnly = false)
    fun withdraw(command: JoinCommand) {
        memberRepository.deleteByEmail(command.email)
    }

    fun getMembers(): List<MemberResult> {
        val findAll: List<Member> = findAll()
        return findAll.stream().map { MemberResult(it) }.toList()
    }

    private fun findAll() = memberRepository.findAll()

    fun getMemberByEmail(email: String): MemberResult =
        MemberResult(this.findByEmail(email).orElseThrow { InvalidInputException(ErrorCode.INVALID_INPUT_VALUE_ERROR) })

    fun getMemberById(id: Long): MemberResult {
        val member = this.findById(id).orElseThrow { InvalidInputException(ErrorCode.INVALID_INPUT_VALUE_ERROR) }
        return MemberResult(member)
    }

    private fun findById(id: Long) = memberRepository.findById(id)

    private fun findByEmail(email: String) = memberRepository.findByEmail(email)
}