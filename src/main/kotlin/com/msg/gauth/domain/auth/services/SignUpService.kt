package com.msg.gauth.domain.auth.services

import com.msg.gauth.domain.auth.presentation.dto.request.SignUpDto
import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.exception.EmailNotVerifiedException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.exception.exceptions.DuplicateEmailException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignUpService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailAuthRepository: EmailAuthRepository
) {
    @Transactional(rollbackFor = [Exception::class])
    fun execute(signUpDto: SignUpDto): Long {
        if (userRepository.existsByEmail(signUpDto.email)) {
            throw DuplicateEmailException()
        }
        val password: String = signUpDto.password
        val user: User = signUpDto.toEntity(passwordEncoder.encode(password))
        val emailAuth = emailAuthRepository.findById(signUpDto.email)
            .orElseThrow { EmailNotVerifiedException() }
        if (!emailAuth.authentication) throw EmailNotVerifiedException()
        return userRepository.save(user).id
    }
}