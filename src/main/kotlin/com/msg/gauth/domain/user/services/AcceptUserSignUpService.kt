package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.user.enums.UserRole
import com.msg.gauth.domain.user.presentation.dto.request.AcceptUserReqDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.TransactionalService

@TransactionalService
class AcceptUserSignUpService(
    private val userRepository: UserRepository
) {

    fun execute(acceptUserReqDto: AcceptUserReqDto){
        val userRole = acceptUserReqDto.userRole
        when(userRole){
            UserRole.ROLE_STUDENT -> createStudent(acceptUserReqDto)
            UserRole.ROLE_TEACHER -> createTeacher(acceptUserReqDto)
            else -> throw
        }

    }

    private fun createStudent(acceptUserReqDto: AcceptUserReqDto){

    }

    private fun createTeacher(acceptUserReqDto: AcceptUserReqDto){

    }

}