package com.msg.gauth.domain.auth.services

import com.msg.gauth.domain.auth.presentation.dto.response.SignupImageResDto
import com.msg.gauth.domain.email.exception.AuthCodeExpiredException
import com.msg.gauth.domain.email.exception.AuthCodeNotVerificationException
import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.global.aws.s3.S3Util
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class SignupImageUploadService(
    private val s3Util: S3Util,
    private val emailAuthRepository: EmailAuthRepository,
){
    fun execute(image: MultipartFile, previousUrl: String?, email: String): SignupImageResDto{
        val emailAuth = emailAuthRepository.findById(email)
            .orElseThrow { throw AuthCodeExpiredException() }
        if(!emailAuth.authentication)
            throw AuthCodeNotVerificationException()
        if(previousUrl != null)
            s3Util.deleteImage(previousUrl)
        return SignupImageResDto(s3Util.upload(image))
    }
}