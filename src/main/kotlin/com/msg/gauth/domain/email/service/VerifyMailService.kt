package com.msg.gauth.domain.email.service

import com.msg.gauth.domain.email.exception.AuthCodeExpiredException
import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.data.repository.findByIdOrNull

@TransactionalService
class VerifyMailService(
    private val emailAuthRepository: EmailAuthRepository
) {

    fun execute(email: String, uuid: String) {
        val emailAuth = emailAuthRepository.findByIdOrNull(email)
            ?: throw AuthCodeExpiredException()

        if (emailAuth.randomValue != uuid)
            throw AuthCodeExpiredException()

        val updateEmailAuth = emailAuth.updateAuthentication(true)

        emailAuthRepository.save(updateEmailAuth)
    }
}