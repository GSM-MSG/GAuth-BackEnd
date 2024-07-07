package com.msg.gauth.domain.email.service

import com.msg.gauth.domain.email.exception.AuthCodeNotVerificationException
import com.msg.gauth.domain.email.repository.EmailAuthRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CheckMailVerificationService(
    private val emailAuthRepository: EmailAuthRepository,
) {

    fun execute(email: String) {
        val authEntity = emailAuthRepository.findByIdOrNull(email)
            ?: throw AuthCodeNotVerificationException()

        if (!authEntity.authentication)
            throw AuthCodeNotVerificationException()
    }
}