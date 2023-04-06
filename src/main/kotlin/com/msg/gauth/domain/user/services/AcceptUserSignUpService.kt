package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.client.exception.BadUserRoleRequestException
import com.msg.gauth.domain.user.enums.UserRole
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.presentation.dto.request.AcceptUserReqDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.TransactionalService

@TransactionalService
class AcceptUserSignUpService(
    private val userRepository: UserRepository
) {

    private fun getUser(id: Long) =
        userRepository.findByIdAndState(id, UserState.PENDING)
            ?: throw UserNotFoundException()

    private fun acceptStudent(id: Long, acceptUserReqDto: AcceptUserReqDto) =
        userRepository.save(getUser(id).update(acceptUserReqDto))


    private fun acceptTeacher(id: Long, acceptUserReqDto: AcceptUserReqDto) =
        userRepository.save(getUser(id).update(acceptUserReqDto.name, acceptUserReqDto.gender))

    fun execute(id: Long, acceptUserReqDto: AcceptUserReqDto) =
        when(acceptUserReqDto.userRole){
            UserRole.ROLE_STUDENT -> acceptStudent(id, acceptUserReqDto)
            UserRole.ROLE_TEACHER -> acceptTeacher(id, acceptUserReqDto)
            else -> throw BadUserRoleRequestException()
        }
}