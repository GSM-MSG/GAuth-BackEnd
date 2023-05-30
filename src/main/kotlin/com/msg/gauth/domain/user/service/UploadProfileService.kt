package com.msg.gauth.domain.user.service

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
import com.msg.gauth.global.thirdparty.aws.s3.S3Util
import org.springframework.web.multipart.MultipartFile

@TransactionalService
class UploadProfileService(
    private val s3Util: S3Util,
    private val userUtil: UserUtil,
    private val userRepository: UserRepository,
){
    fun execute(file: MultipartFile){
        val url = s3Util.upload(file)
        val user = userUtil.fetchCurrentUser()

        userRepository.save(
            User(
                id = user.id,
                email = user.email,
                password = user.password,
                gender = user.gender,
                grade = user.grade,
                classNum = user.classNum,
                num = user.num,
                roles = user.roles,
                state = user.state,
                profileUrl = url,
                wrongPasswordCount = user.wrongPasswordCount,
                oauthWrongPasswordCount = user.oauthWrongPasswordCount
            )
        )
    }
}