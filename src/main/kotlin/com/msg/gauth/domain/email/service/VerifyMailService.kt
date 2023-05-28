package com.msg.gauth.domain.email.service

import com.msg.gauth.domain.email.exception.AuthCodeExpiredException
import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.global.annotation.service.TransactionalService

@TransactionalService
class VerifyMailService(
    private val emailAuthRepository: EmailAuthRepository
) {
    fun execute(email: String, uuid: String) {
        val emailAuth = emailAuthRepository.findById(email)
            .orElseThrow { AuthCodeExpiredException() }

        if (emailAuth.randomValue != uuid) throw AuthCodeExpiredException()

        val updateEmailAuth = emailAuth.updateAuthentication(true)

        emailAuthRepository.save(updateEmailAuth)
    }
}