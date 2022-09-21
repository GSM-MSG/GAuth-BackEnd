package com.msg.gauth.domain.client.services

import com.msg.gauth.domain.client.persentation.dto.response.ClientAllResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.global.util.CurrentUserUtil
import org.springframework.stereotype.Service

@Service
class GetAllClientsService(
    private val clientRepository: ClientRepository,
    private val currentUserUtil: CurrentUserUtil,
){
    fun execute(): List<ClientAllResDto> =
        clientRepository.findAllByCreatedBy(currentUserUtil.getCurrentUser())
            .map { ClientAllResDto(it) }
}