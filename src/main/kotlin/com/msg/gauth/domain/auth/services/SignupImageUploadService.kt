package com.msg.gauth.domain.auth.services

import com.msg.gauth.domain.auth.presentation.dto.response.SignupImageResDto
import com.msg.gauth.global.aws.s3.S3Util
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class SignupImageUploadService(
    private val s3Util: S3Util,
){
    fun execute(image: MultipartFile, previousUrl: String?): SignupImageResDto{
        if(previousUrl != null)
            s3Util.deleteImage(previousUrl)
        return SignupImageResDto(s3Util.upload(image))
    }
}