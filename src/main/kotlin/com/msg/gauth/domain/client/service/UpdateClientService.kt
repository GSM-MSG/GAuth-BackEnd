package com.msg.gauth.domain.client.service

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.presentation.dto.request.ClientUpdateReqDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService

@TransactionalService
class UpdateClientService(
    private val clientRepository: ClientRepository,
    private val userUtil: UserUtil
) {
    fun updateClient(id: Long, clientUpdateReqDto: ClientUpdateReqDto){
        val client = clientRepository.findByIdAndCreatedBy(id, userUtil.fetchCurrentUser()) ?: throw ClientNotFindException()
        clientRepository.save(
            Client(
                id = id,
                clientId = client.clientId,
                clientSecret = client.clientSecret,
                redirectUri = clientUpdateReqDto.redirectUri,
                serviceName = clientUpdateReqDto.serviceName,
                serviceUri = clientUpdateReqDto.serviceUri,
                createdBy = client.createdBy
            )
        )
    }
}