package com.msg.gauth.domain.auth.service

import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.ReadOnlyService
import com.msg.gauth.global.exception.exceptions.DuplicateEmailException

@ReadOnlyService
class SignUpEmailVerificationService(
    private val userRepository: UserRepository
) {
    fun execute(email: String) {
        if (userRepository.existsByEmail(email))
            throw DuplicateEmailException()
    }
}