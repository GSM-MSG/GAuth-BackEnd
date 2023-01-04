package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.domain.user.exception.EmailNotVerifiedException
import com.msg.gauth.domain.user.presentation.dto.request.PasswordChangeReqDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.utils.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
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
        val emailAuth = emailAuthRepository.findById(currentUser.email)
            .orElseThrow{ throw EmailNotVerifiedException() }
        if(!emailAuth.authentication)
            throw EmailNotVerifiedException()
        val update = currentUser.update(passwordEncoder.encode(passwordChangeReqDto.password))
        userRepository.save(update)
        emailAuthRepository.delete(emailAuth)
    }
}