package com.msg.gauth.domain.auth.service

import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.domain.user.exception.EmailNotVerifiedException
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.auth.presentation.dto.request.PasswordInitReqDto
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.security.crypto.password.PasswordEncoder

@TransactionalService
class InitPasswordService(
    private val userRepository: UserRepository,
    private val emailAuthRepository: EmailAuthRepository,
    private val passwordEncoder: PasswordEncoder,
){
    fun execute(passwordInitReqDto: PasswordInitReqDto){
        val emailAuth = emailAuthRepository.findById(passwordInitReqDto.email)
            .orElseThrow { throw EmailNotVerifiedException() }
        if(!emailAuth.authentication)
            throw EmailNotVerifiedException()
        val user = userRepository.findByEmail(passwordInitReqDto.email)
            ?: throw UserNotFoundException()

        user.let {
            userRepository.save(
                User(
                    id = it.id,
                    email = it.email,
                    password = passwordEncoder.encode(passwordInitReqDto.newPassword),
                    gender = it.gender,
                    name = it.name,
                    grade = it.grade,
                    classNum = it.classNum,
                    num = it.num,
                    roles = it.roles,
                    state = it.state,
                    profileUrl = it.profileUrl
                )
            )
        }
        emailAuthRepository.delete(emailAuth)
    }
}