package com.msg.gauth.domain.email.services

import com.msg.gauth.domain.email.exception.AuthCodeExpiredException
import com.msg.gauth.domain.email.repository.EmailAuthRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MailVerificationService(
    private val emailAuthRepository: EmailAuthRepository
) {

    @Transactional
    fun execute(email: String, uuid: String) {
        val emailAuth = emailAuthRepository.findById(email)
            .orElseThrow { AuthCodeExpiredException() }
        if (emailAuth.randomValue != uuid) throw AuthCodeExpiredException()
        emailAuth.updateAuthentication(true)
        emailAuthRepository.save(emailAuth)
    }
}