package com.msg.gauth.domain.auth.service

import com.msg.gauth.domain.auth.exception.PasswordMismatchException
import com.msg.gauth.domain.auth.presentation.dto.request.PasswordUpdateRequestDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.security.crypto.password.PasswordEncoder

@TransactionalService
class UpdatePasswordService(
    private val passwordEncoder: PasswordEncoder,
    private val userUtil: UserUtil,
    private val userRepository: UserRepository
) {

    fun execute(passwordUpdateRequestDto: PasswordUpdateRequestDto) {
        val currentUser = userUtil.fetchCurrentUser()

        if (currentUser.password == passwordUpdateRequestDto.password) {
            throw PasswordMismatchException()
        }

        userRepository.save(passwordUpdateRequestDto.toEntity(currentUser, passwordEncoder.encode(passwordUpdateRequestDto.newPassword)))
    }
}