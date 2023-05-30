package com.msg.gauth.domain.user.service

import com.msg.gauth.domain.client.exception.BadUserRoleRequestException
import com.msg.gauth.domain.user.User
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

    private fun acceptStudent(id: Long, acceptUserReqDto: AcceptUserReqDto)=
        userRepository.save(acceptUserReqDto.toStudentEntity(getUser(id)))

    private fun acceptTeacher(id: Long, acceptUserReqDto: AcceptUserReqDto) =
        userRepository.save(acceptUserReqDto.toTeacherEntity(getUser(id)))

    private fun acceptGraduate(id: Long, acceptUserReqDto: AcceptUserReqDto) =
        userRepository.save(acceptUserReqDto.toGraduateEntity(getUser(id)))

}