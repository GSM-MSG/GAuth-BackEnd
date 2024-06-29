package com.msg.gauth.domain.client.service

import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.exception.UserNotMatchException
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.data.repository.findByIdOrNull

@TransactionalService
class DeleteClientService(
    private val clientRepository: ClientRepository,
    private val userUtil: UserUtil,
) {

    fun execute(id: Long){
        val client = clientRepository.findByIdOrNull(id)
            ?: throw ClientNotFindException()

        if(client.createdBy != userUtil.fetchCurrentUser())
            throw UserNotMatchException()

        clientRepository.delete(client)
    }
}