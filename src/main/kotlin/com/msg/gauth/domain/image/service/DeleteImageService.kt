package com.msg.gauth.domain.image.service

import com.msg.gauth.global.thirdparty.aws.s3.S3Util
import org.springframework.stereotype.Service

@Service
class DeleteImageService(
    private val s3Util: S3Util
) {
    fun execute(url: String) {
        s3Util.deleteImage(url)
    }
}