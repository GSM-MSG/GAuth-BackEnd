package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.user.presentation.dto.request.AcceptTeacherReqDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.utils.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService

@TransactionalService
class AcceptTeacherSignUpService(
    userUtil: UserUtil,
    userRepository: UserRepository
) {

    fun execute(acceptTeacherReqDto: AcceptTeacherReqDto) {
        
    }
}