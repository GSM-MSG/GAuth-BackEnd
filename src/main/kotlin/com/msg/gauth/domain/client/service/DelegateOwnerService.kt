package com.msg.gauth.domain.client.service

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.exception.BadRequestException
import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.exception.UserNotFoundException
import com.msg.gauth.domain.client.exception.UserNotMatchException
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.data.repository.findByIdOrNull

@TransactionalService
class DelegateOwnerService(
    private val clientRepository: ClientRepository,
    private val userUtil: UserUtil,
    private val userRepository: UserRepository
) {
    fun execute(clientId: Long, userId: Long) {
        if (clientId == userId)
            throw BadRequestException()

        val user = userUtil.fetchCurrentUser()

        val client = clientRepository.findByIdOrNull(clientId)
            ?: throw ClientNotFindException()

        if (user != client.createdBy)
            throw UserNotMatchException()

        val updateUser = userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException()

        val updateClient = Client(
            id = client.id,
            clientId = client.clientId,
            clientSecret = client.clientSecret,
            redirectUri = client.redirectUri,
            serviceName = client.serviceName,
            serviceUri = client.serviceUri,
            createdBy = updateUser,
            serviceScope = client.serviceScope,
            serviceImgUrl = client.serviceImgUrl
        )

        clientRepository.save(updateClient)
    }

}