package com.msg.gauth.domain.user.service

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService

@TransactionalService
class UploadProfileURLService(
    private val userUtil: UserUtil,
    private val userRepository: UserRepository,
){
    fun execute(imageURL: String) {
        val user = userUtil.fetchCurrentUser()

        userRepository.save(
            User(
                id = user.id,
                email = user.email,
                password = user.password,
                name = user.name,
                gender = user.gender,
                grade = user.grade,
                classNum = user.classNum,
                num = user.num,
                state = user.state,
                profileUrl = imageURL,
                wrongPasswordCount = user.wrongPasswordCount,
                oauthWrongPasswordCount = user.oauthWrongPasswordCount
            )
        )
    }
}