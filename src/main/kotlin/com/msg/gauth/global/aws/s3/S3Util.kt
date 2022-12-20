package com.msg.gauth.global.aws.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
class S3Util(
    private val amazonS3: AmazonS3,
){
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String? = null
    fun upload(file: MultipartFile): String {
        val profileName = UUID.randomUUID().toString() + "-" + file.originalFilename
        val metadata = ObjectMetadata()
        metadata.contentLength = file.inputStream.available().toLong()
        amazonS3.putObject(bucket, profileName, file.inputStream, metadata)
        return amazonS3.getUrl(bucket, profileName).toString()
    }

    fun deleteImage(url: String){
        val key = url.split("/")[3]
        amazonS3.deleteObject(bucket, key)
    }
}