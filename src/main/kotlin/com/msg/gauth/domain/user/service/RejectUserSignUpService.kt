package com.msg.gauth.domain.user.service

import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.TransactionalService

@TransactionalService
class RejectUserSignUpService(
    private val userRepository: UserRepository
) {

    fun execute(id: Long) =
        userRepository.findByIdAndState(id, UserState.PENDING)
            .let { it ?: throw UserNotFoundException() }
            .let { userRepository.delete(it) }

}