package com.msg.gauth.domain.user.service

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserRoleType
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
        val user: User = userRepository.findByIdAndStateAndUserRole(acceptTeacherReqDto.id, UserState.PENDING, UserRoleType.ROLE_TEACHER)
            ?: throw UserNotFoundException()

        userRepository.save(acceptTeacherReqDto.toEntity(user))
    }
}