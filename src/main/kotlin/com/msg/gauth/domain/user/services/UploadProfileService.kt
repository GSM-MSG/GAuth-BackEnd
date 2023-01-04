package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.utils.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
import com.msg.gauth.global.aws.s3.S3Util
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
        val update = user.updateProfile(url)
        userRepository.save(update)
    }
}