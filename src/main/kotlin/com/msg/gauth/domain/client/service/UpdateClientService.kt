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
    private val userUtil: UserUtil
) {
    fun updateClient(id: Long, clientUpdateReqDto: ClientUpdateReqDto){
        val client = clientRepository.findByIdAndCreatedBy(id, userUtil.fetchCurrentUser())
            ?: throw ClientNotFindException()

        clientRepository.save(clientUpdateReqDto.toEntity(client))
    }
}