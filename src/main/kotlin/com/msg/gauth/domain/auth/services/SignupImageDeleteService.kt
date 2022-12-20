package com.msg.gauth.domain.auth.services

import com.msg.gauth.domain.auth.presentation.dto.request.SignupImageDeleteReqDto
import com.msg.gauth.global.aws.s3.S3Util
import org.springframework.stereotype.Service

@Service
class SignupImageDeleteService(
    private val s3Util: S3Util,
){
    fun execute(signupImageDeleteReqDto: SignupImageDeleteReqDto) =
        s3Util.deleteImage(signupImageDeleteReqDto.imageUrl)
}