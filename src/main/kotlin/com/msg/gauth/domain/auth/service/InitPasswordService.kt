package com.msg.gauth.domain.auth.service

import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.domain.user.exception.EmailNotVerifiedException
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.auth.presentation.dto.request.InitPasswordRequestDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.security.crypto.password.PasswordEncoder

@TransactionalService
class InitPasswordService(
    private val userRepository: UserRepository,
    private val emailAuthRepository: EmailAuthRepository,
    private val passwordEncoder: PasswordEncoder,
){

    fun execute(initPasswordRequestDto: InitPasswordRequestDto) {
        val emailAuth = emailAuthRepository.findById(initPasswordRequestDto.email)
            .orElseThrow { throw EmailNotVerifiedException() }

        if(!emailAuth.authentication)
            throw EmailNotVerifiedException()

        val user = userRepository.findByEmail(initPasswordRequestDto.email)
            ?: throw UserNotFoundException()

        userRepository.save(initPasswordRequestDto.toEntity(user, passwordEncoder.encode(initPasswordRequestDto.newPassword)))
        emailAuthRepository.delete(emailAuth)
    }
}