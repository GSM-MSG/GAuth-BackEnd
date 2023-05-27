package com.msg.gauth.domain.user.service

import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.domain.user.exception.EmailNotVerifiedException
import com.msg.gauth.domain.user.presentation.dto.request.PasswordChangeReqDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder

@TransactionalService
class ChangePasswordService(
    private val emailAuthRepository: EmailAuthRepository,
    private val userUtil: UserUtil,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
){
    fun execute(passwordChangeReqDto: PasswordChangeReqDto){
        val currentUser = userUtil.fetchCurrentUser()
        val emailAuth = emailAuthRepository.findByIdOrNull(currentUser.email)
            ?: throw EmailNotVerifiedException()

        if(!emailAuth.authentication)
            throw EmailNotVerifiedException()

        val update = passwordChangeReqDto.toEntity(currentUser,
            passwordEncoder.encode(passwordChangeReqDto.password))

        userRepository.save(update)
        emailAuthRepository.delete(emailAuth)
    }
}