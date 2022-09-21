package com.msg.gauth.domain.client.services

import com.msg.gauth.domain.client.exception.UserNotSameException
import com.msg.gauth.domain.client.persentation.dto.response.ClientOneResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.global.util.CurrentUserUtil
import org.springframework.stereotype.Service

@Service
class GetOneClientService(
    private val clientRepository: ClientRepository,
    private val currentUserUtil: CurrentUserUtil,
){
    fun execute(clientId: String): ClientOneResDto{
        val client = clientRepository.findClientByClientId(clientId)!!
        if(client.createdBy != currentUserUtil.getCurrentUser())
            throw UserNotSameException()
        return ClientOneResDto(client)
    }
}