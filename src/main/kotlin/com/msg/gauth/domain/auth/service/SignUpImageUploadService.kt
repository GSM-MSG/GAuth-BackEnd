package com.msg.gauth.domain.auth.service

import com.msg.gauth.domain.auth.presentation.dto.response.SignupImageResDto
import com.msg.gauth.domain.email.exception.AuthCodeExpiredException
import com.msg.gauth.domain.email.exception.AuthCodeNotVerificationException
import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.global.annotation.service.TransactionalService
import com.msg.gauth.global.thirdparty.aws.s3.S3Util
import org.springframework.web.multipart.MultipartFile

@TransactionalService
class SignUpImageUploadService(
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