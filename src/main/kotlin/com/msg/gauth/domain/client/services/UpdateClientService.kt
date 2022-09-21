package com.msg.gauth.domain.client.services

import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.persentation.dto.request.ClientUpdateReqDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.global.util.CurrentUserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateClientService(
    private val clientRepository: ClientRepository,
    private val currentUserUtil: CurrentUserUtil,
) {
    @Transactional
    fun updateClient(clientId: String, clientUpdateReqDto: ClientUpdateReqDto){
        val client = clientRepository.findByClientIdAndCreatedBy(clientId, currentUserUtil.getCurrentUser()) ?: throw ClientNotFindException()
        client.update(clientUpdateReqDto)
    }
}