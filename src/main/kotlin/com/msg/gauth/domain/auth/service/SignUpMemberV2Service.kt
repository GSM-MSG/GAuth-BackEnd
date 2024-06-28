package com.msg.gauth.domain.auth.service

import com.msg.gauth.domain.auth.event.SignupLoggingEvent
import com.msg.gauth.domain.auth.exception.AlreadyExistUserException
import com.msg.gauth.domain.auth.presentation.dto.request.SignUpV2RequestDto
import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.EmailNotVerifiedException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.TransactionalService
import com.msg.gauth.global.exception.exceptions.DuplicateEmailException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder

@TransactionalService
class SignUpMemberV2Service(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailAuthRepository: EmailAuthRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun execute(signUpV2RequestDto: SignUpV2RequestDto) {
        if(userRepository.existsByEmail(signUpV2RequestDto.email))
            throw DuplicateEmailException()

        if (userRepository.existsByGradeAndClassNumAndNum(signUpV2RequestDto.grade, signUpV2RequestDto.classNum, signUpV2RequestDto.num))
            throw AlreadyExistUserException()

        val emailAuth = emailAuthRepository.findById(signUpV2RequestDto.email)
            .orElseThrow { EmailNotVerifiedException() }

        if (!emailAuth.authentication) {
            emailAuthRepository.delete(emailAuth)
            throw EmailNotVerifiedException()
        }

        val user = User(
            email = signUpV2RequestDto.email,
            password = passwordEncoder.encode(signUpV2RequestDto.password),
            state = UserState.PENDING,
            grade = signUpV2RequestDto.grade,
            classNum = signUpV2RequestDto.classNum,
            num = signUpV2RequestDto.num,
            name = signUpV2RequestDto.name,
            profileUrl = null
        )

        emailAuthRepository.delete(emailAuth)

        userRepository.save(user)

        applicationEventPublisher.publishEvent(SignupLoggingEvent(user.email))
    }
}