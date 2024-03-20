package com.msg.gauth.domain.auth.service

import com.msg.gauth.domain.auth.exception.PasswordAndNewPasswordSameException
import com.msg.gauth.domain.auth.exception.PasswordMismatchException
import com.msg.gauth.domain.auth.presentation.dto.request.UpdatePasswordRequestDto
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

    fun execute(updatePasswordRequestDto: UpdatePasswordRequestDto) {
        val currentUser = userUtil.fetchCurrentUser()

        if (!passwordEncoder.matches(updatePasswordRequestDto.password, currentUser.password)) {
            throw PasswordMismatchException()
        }

        if (updatePasswordRequestDto.password == updatePasswordRequestDto.newPassword) {
            throw PasswordAndNewPasswordSameException()
        }

        val newPassword = passwordEncoder.encode(updatePasswordRequestDto.newPassword)

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