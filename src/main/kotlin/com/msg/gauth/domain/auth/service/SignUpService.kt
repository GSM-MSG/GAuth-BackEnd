package com.msg.gauth.domain.auth.service

import com.msg.gauth.domain.auth.presentation.dto.request.SignUpDto
import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.UserRole
import com.msg.gauth.domain.user.enums.UserRoleType
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.EmailNotVerifiedException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.repository.UserRoleRepository
import com.msg.gauth.global.annotation.service.TransactionalService
import com.msg.gauth.global.exception.exceptions.DuplicateEmailException
import org.springframework.security.crypto.password.PasswordEncoder

@TransactionalService
class SignUpService(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailAuthRepository: EmailAuthRepository
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

        val emailAuth = emailAuthRepository.findById(signUpDto.email)
            .orElseThrow { EmailNotVerifiedException() }

        if (!emailAuth.authentication) {
            emailAuthRepository.delete(emailAuth)
            throw EmailNotVerifiedException()
        }

        emailAuthRepository.delete(emailAuth)

        val savedUser = userRepository.save(user)

        saveUserRole(user)

        return savedUser.id
    }

    private fun saveUserRole(user: User) {
        val userRole = UserRole(
            user = user,
            userRoleType = UserRoleType.ROLE_STUDENT
        )

        userRoleRepository.save(userRole)
    }
}