package com.msg.gauth.domain.auth.service

import com.msg.gauth.domain.auth.exception.PasswordAndNewPasswordSameException
import com.msg.gauth.domain.auth.exception.PasswordMismatchException
import com.msg.gauth.domain.auth.presentation.dto.request.PasswordUpdateRequestDto
import com.msg.gauth.domain.user.User
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

        if (!passwordEncoder.matches(passwordUpdateRequestDto.password, currentUser.password)) {
            throw PasswordMismatchException()
        }

        if (passwordUpdateRequestDto.password == passwordUpdateRequestDto.newPassword) {
            throw PasswordAndNewPasswordSameException()
        }

        val newPassword = passwordEncoder.encode(passwordUpdateRequestDto.newPassword)

        val user = User(
            id = currentUser.id,
            email = currentUser.email,
            password = newPassword,
            gender = currentUser.gender,
            name = currentUser.name,
            grade = currentUser.grade,
            classNum = currentUser.classNum,
            num = currentUser.num,
            state = currentUser.state,
            profileUrl = currentUser.profileUrl
        )

        userRepository.save(user)
    }
}