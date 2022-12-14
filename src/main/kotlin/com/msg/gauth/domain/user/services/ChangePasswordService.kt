package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.domain.user.exception.EmailNotVerifiedException
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
        val emailAuth = emailAuthRepository.findById(userUtil.fetchCurrentUser().email)
            .orElseThrow{ throw EmailNotVerifiedException() }
        if(!emailAuth.authentication)
            throw EmailNotVerifiedException()
        val user = (userRepository.findByEmail(emailAuth.email)
            ?: throw EmailNotVerifiedException())
        val update = user.update(passwordEncoder.encode(passwordChangeReqDto.password))
        userRepository.save(update)
        emailAuthRepository.delete(emailAuth)
    }
}