package com.msg.gauth.domain.client.services

import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.exception.UserNotMatchException
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.user.utils.UserUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteClientService(
    private val clientRepository: ClientRepository,
    private val userUtil: UserUtil,
){
    @Transactional(rollbackFor = [Exception::class])
    fun execute(id: Long){
        val client = clientRepository.findByIdOrNull(id)
            ?: throw ClientNotFindException()
        if(client.createdBy != userUtil.fetchCurrentUser())
            throw UserNotMatchException()
        clientRepository.delete(client)
    }
}