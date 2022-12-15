package com.msg.gauth.domain.user.services

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.utils.UserUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
class UploadProfileService(
    private val amazonS3: AmazonS3,
    private val userUtil: UserUtil,
    private val userRepository: UserRepository,
){
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String? = null

    @Transactional(rollbackFor = [Exception::class])
    fun execute(file: MultipartFile){
        val profileName = UUID.randomUUID().toString() + "-" + file.originalFilename
        val metadata = ObjectMetadata()
        metadata.contentLength = file.inputStream.available().toLong()
        amazonS3.putObject(bucket, profileName, file.inputStream, metadata)
        val url = amazonS3.getUrl(bucket, profileName).toString()
        val user = userUtil.fetchCurrentUser()
        val update = user.updateProfile(url)
        userRepository.save(update)
    }
}