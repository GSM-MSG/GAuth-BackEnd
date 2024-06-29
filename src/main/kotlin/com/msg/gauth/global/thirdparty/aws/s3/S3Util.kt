package com.msg.gauth.global.thirdparty.aws.s3

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.msg.gauth.domain.admin.exception.FileExtensionInvalidException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
class S3Util(
    private val amazonS3: AmazonS3,
) {
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String? = null

    fun imageUpload(image: MultipartFile): String {
        val list = listOf("jpg", "jpeg", "png", "gif")

        val splitFile = image.originalFilename.toString().split(".")

        if(splitFile.size < 2)
            throw FileExtensionInvalidException()

        val extension = splitFile[1].lowercase()

        if(list.none { it == extension })
            throw FileExtensionInvalidException()

        return upload(image)
    }

    private fun upload(file: MultipartFile): String {
        val profileName = "${bucket}/${UUID.randomUUID()}${file.originalFilename}"

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