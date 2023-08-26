package com.msg.gauth.domain.client.service

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.presentation.dto.request.ClientUpdateReqDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
import com.msg.gauth.global.thirdparty.aws.s3.S3Util
import org.springframework.web.multipart.MultipartFile

@TransactionalService
class UpdateClientService(
    private val clientRepository: ClientRepository,
    private val userUtil: UserUtil,
    private val s3Util: S3Util
) {
    fun updateClient(id: Long, clientUpdateReqDto: ClientUpdateReqDto, image: MultipartFile?){
        val client = clientRepository.findByIdAndCreatedBy(id, userUtil.fetchCurrentUser())
            ?: throw ClientNotFindException()

        val serviceImgUrl = if(image != null) {
            s3Util.deleteImage(client.serviceImgUrl)
            s3Util.upload(image)
        } else {
            client.serviceImgUrl
        }

        clientRepository.save(clientUpdateReqDto.toEntity(client, serviceImgUrl))
    }
}