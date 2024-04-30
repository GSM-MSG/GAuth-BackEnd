package com.msg.gauth.domain.user.service

import com.msg.gauth.domain.client.exception.BadUserRoleRequestException
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.UserRole
import com.msg.gauth.domain.user.enums.UserRoleType
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.presentation.dto.request.AcceptUserReqDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.repository.UserRoleRepository
import com.msg.gauth.global.annotation.service.TransactionalService
@TransactionalService
class AcceptUserSignUpService(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository
) {

    fun execute(id: Long, acceptUserReqDto: AcceptUserReqDto) =
        when(acceptUserReqDto.userRoleType){
            UserRoleType.ROLE_STUDENT -> acceptStudent(id, acceptUserReqDto)
            UserRoleType.ROLE_TEACHER -> acceptTeacher(id, acceptUserReqDto)
            UserRoleType.ROLE_GRADUATE -> acceptGraduate(id, acceptUserReqDto)
            else -> throw BadUserRoleRequestException()
        }

    private fun getUser(id: Long) =
        userRepository.findByIdAndState(id, UserState.PENDING)
            ?: throw UserNotFoundException()

    private fun acceptStudent(id: Long, acceptUserReqDto: AcceptUserReqDto) {
        val user = acceptUserReqDto.toStudentEntity(getUser(id))
        saveUserRole(user, acceptUserReqDto.userRoleType)
        userRepository.save(user)
    }

    private fun acceptTeacher(id: Long, acceptUserReqDto: AcceptUserReqDto) {
        val user = acceptUserReqDto.toTeacherEntity(getUser(id))
        saveUserRole(user, acceptUserReqDto.userRoleType)
        userRepository.save(user)
    }

    private fun acceptGraduate(id: Long, acceptUserReqDto: AcceptUserReqDto) {
        val user = acceptUserReqDto.toGraduateEntity(getUser(id))
        saveUserRole(user, acceptUserReqDto.userRoleType)
        userRepository.save(user)
    }

    private fun saveUserRole(user: User, userRoleType: UserRoleType) {
        val userRole = UserRole(
            user = user,
            userRoleType = userRoleType
        )
        userRoleRepository.save(userRole)
    }


}

