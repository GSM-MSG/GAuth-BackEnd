package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.domain.user.exception.EmailNotVerifiedException
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.presentation.dto.request.PasswordChangeReqDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.utils.UserUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangePasswordService(
    private val emailAuthRepository: EmailAuthRepository,
    private val userUtil: UserUtil,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
){
    @Transactional(rollbackFor = [Exception::class])
    fun execute(passwordChangeReqDto: PasswordChangeReqDto){
        val currentUser = userUtil.fetchCurrentUser()
        val emailAuth = emailAuthRepository.findById(currentUser.email)
            .orElseThrow{ throw EmailNotVerifiedException() }
        if(!emailAuth.authentication)
            throw EmailNotVerifiedException()
        if(!userRepository.existsByEmail(currentUser.email))
            throw UserNotFoundException()
        val update = currentUser.update(passwordEncoder.encode(passwordChangeReqDto.password))
        userRepository.save(update)
        emailAuthRepository.delete(emailAuth)
    }
}