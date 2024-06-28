package com.msg.gauth.domain.auth.service

import com.msg.gauth.domain.auth.event.SignupLoggingEvent
import com.msg.gauth.domain.auth.presentation.dto.request.SignUpDto
import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.EmailNotVerifiedException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.TransactionalService
import com.msg.gauth.global.exception.exceptions.DuplicateEmailException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder

@TransactionalService
class SignUpService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailAuthRepository: EmailAuthRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun execute(signUpDto: SignUpDto): Long {
        if (userRepository.existsByEmail(signUpDto.email)) {
            emailAuthRepository.deleteById(signUpDto.email)
            throw DuplicateEmailException()
        }

        val user = User(
            email = signUpDto.email,
            password = passwordEncoder.encode(signUpDto.password),
            state = UserState.PENDING,
            profileUrl = null
        )

        val emailAuth = emailAuthRepository.findByIdOrNull(signUpDto.email)
            ?: throw EmailNotVerifiedException()

        if (!emailAuth.authentication) {
            emailAuthRepository.delete(emailAuth)
            throw EmailNotVerifiedException()
        }

        emailAuthRepository.delete(emailAuth)

        val savedUser = userRepository.save(user)

        applicationEventPublisher.publishEvent(SignupLoggingEvent(user.email))

        return savedUser.id
    }
}