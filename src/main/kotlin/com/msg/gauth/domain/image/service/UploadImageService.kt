package com.msg.gauth.domain.image.service

import com.msg.gauth.domain.admin.exception.FileExtensionInvalidException
import com.msg.gauth.domain.image.presentation.dto.response.UploadImageResDto
import com.msg.gauth.global.thirdparty.aws.s3.S3Util
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UploadImageService(
    private val s3Util: S3Util
) {
    fun execute(image: MultipartFile): UploadImageResDto {
        val list = listOf("jpg", "png", "gif")

        val extension = image.originalFilename.toString().split(".")[1]

        if(!list.contains(extension))
            throw FileExtensionInvalidException()

        val imgURL = s3Util.upload(image)

        return UploadImageResDto(imgURL)
    }
}