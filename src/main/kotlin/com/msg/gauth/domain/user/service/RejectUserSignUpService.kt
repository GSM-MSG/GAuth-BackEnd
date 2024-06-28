package com.msg.gauth.domain.user.service

import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.TransactionalService

@TransactionalService
class RejectUserSignUpService(
    private val userRepository: UserRepository
) {

    fun execute(id: Long) {
        val user = userRepository.findByIdAndState(id, UserState.PENDING)
            ?: throw UserNotFoundException()

        userRepository.delete(user)
    }
}