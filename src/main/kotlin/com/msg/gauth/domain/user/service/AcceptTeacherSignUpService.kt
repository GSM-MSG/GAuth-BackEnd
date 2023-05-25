package com.msg.gauth.domain.user.service

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserRole
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.presentation.dto.request.AcceptTeacherReqDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.TransactionalService

@TransactionalService
class AcceptTeacherSignUpService(
    private val userRepository: UserRepository
) {

    fun execute(acceptTeacherReqDto: AcceptTeacherReqDto) {
        val user: User = userRepository.findByIdAndStateAndRoles(acceptTeacherReqDto.id, UserState.PENDING, mutableListOf(UserRole.ROLE_TEACHER))
            ?: throw UserNotFoundException()
        user.updateTeacher(acceptTeacherReqDto.name, acceptTeacherReqDto.gender)
            .let { userRepository.save(it) }
    }
}