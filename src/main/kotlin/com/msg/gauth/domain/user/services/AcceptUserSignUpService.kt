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

    fun execute(id: Long, acceptUserReqDto: AcceptUserReqDto) =
        when(acceptUserReqDto.userRole){
            UserRole.ROLE_STUDENT -> acceptStudent(id, acceptUserReqDto)
            UserRole.ROLE_TEACHER -> acceptTeacher(id, acceptUserReqDto)
            UserRole.ROLE_GRADUATE -> acceptGraduate(id, acceptUserReqDto)
            else -> throw BadUserRoleRequestException()
        }

    private fun getUser(id: Long) =
        userRepository.findByIdAndState(id, UserState.PENDING)
            ?: throw UserNotFoundException()

    private fun acceptStudent(id: Long, acceptUserReqDto: AcceptUserReqDto) =
        getUser(id).update(acceptUserReqDto)
            .let { userRepository.save(it) }


    private fun acceptTeacher(id: Long, acceptUserReqDto: AcceptUserReqDto) =
        getUser(id).updateTeacher(acceptUserReqDto.name, acceptUserReqDto.gender)
            .let { userRepository.save(it) }

    private fun acceptGraduate(id: Long, acceptUserReqDto: AcceptUserReqDto) =
        getUser(id).updateGraduate(acceptUserReqDto.name, acceptUserReqDto.gender)
            .let { userRepository.save(it) }

}