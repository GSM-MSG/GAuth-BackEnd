package com.msg.gauth.domain.client.services

import com.msg.gauth.domain.client.persentation.dto.response.ClientAllResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.global.util.CurrentUserUtil
import org.springframework.stereotype.Service

@Service
class GetAllClientsService(
    val clientRepository: ClientRepository,
    val currentUserUtil: CurrentUserUtil,
){
    fun execute():List<ClientAllResDto>{
        val findAllByCreatedBy = clientRepository.findAllByCreatedBy(currentUserUtil.getCurrentUser())
        val list = mutableListOf<ClientAllResDto>()
        findAllByCreatedBy.forEach {
            list.add(ClientAllResDto(it))
        }
        return list
    }
}