package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.utils.UserUtil
import com.msg.gauth.global.aws.s3.S3Util
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class UploadProfileService(
    private val s3Util: S3Util,
    private val userUtil: UserUtil,
    private val userRepository: UserRepository,
){

    @Transactional(rollbackFor = [Exception::class])
    fun execute(file: MultipartFile){
        val url = s3Util.upload(file)
        val user = userUtil.fetchCurrentUser()
        val update = user.updateProfile(url)
        userRepository.save(update)
    }
}