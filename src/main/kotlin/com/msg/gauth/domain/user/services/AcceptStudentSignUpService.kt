package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.user.enums.UserRole
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.presentation.dto.request.AcceptStudentReqDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.TransactionalService

@TransactionalService
class AcceptStudentSignUpService(
    val userRepository: UserRepository
) {
    fun execute(acceptedStudentReqDto: AcceptStudentReqDto) {
        val user = userRepository.findByIdAndStateAndRoles(acceptedStudentReqDto.id, UserState.PENDING, mutableListOf(UserRole.ROLE_STUDENT))
            ?: throw UserNotFoundException()

        userRepository.save(user.update(acceptedStudentReqDto.name, acceptedStudentReqDto.grade, acceptedStudentReqDto.classNum, acceptedStudentReqDto.num,acceptedStudentReqDto.gender))
    }
}