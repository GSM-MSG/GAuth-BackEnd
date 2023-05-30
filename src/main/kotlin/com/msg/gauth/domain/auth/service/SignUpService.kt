package com.msg.gauth.domain.auth.service

import com.msg.gauth.domain.auth.presentation.dto.request.SignUpDto
import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.exception.EmailNotVerifiedException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.TransactionalService
import com.msg.gauth.global.exception.exceptions.DuplicateEmailException
import org.springframework.security.crypto.password.PasswordEncoder

@TransactionalService
class SignUpService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailAuthRepository: EmailAuthRepository
) {
    fun execute(signUpDto: SignUpDto): Long {
        if (userRepository.existsByEmail(signUpDto.email)) {
            emailAuthRepository.deleteById(signUpDto.email)
            throw DuplicateEmailException()
        }
        val password: String = signUpDto.password
        val user: User = signUpDto.toEntity(passwordEncoder.encode(password))
        val emailAuth = emailAuthRepository.findById(signUpDto.email)
            .orElseThrow { EmailNotVerifiedException() }
        if (!emailAuth.authentication) {
            emailAuthRepository.delete(emailAuth)
            throw EmailNotVerifiedException()
        }
        emailAuthRepository.delete(emailAuth)
        return userRepository.save(user).id
    }
}