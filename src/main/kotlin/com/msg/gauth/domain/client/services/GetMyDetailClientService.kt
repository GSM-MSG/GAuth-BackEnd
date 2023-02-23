package com.msg.gauth.domain.client.services

import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.presentation.dto.response.ClientDetailResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.user.utils.UserUtil
import com.msg.gauth.global.annotation.service.ReadOnlyService

@ReadOnlyService
class GetMyDetailClientService(
    private val clientRepository: ClientRepository,
    private val userUtil: UserUtil
) {
    fun execute(id: Long): ClientDetailResDto{
        val client = clientRepository.findByIdAndCreatedBy(id, userUtil.fetchCurrentUser()) ?: throw ClientNotFindException()
        return ClientDetailResDto(client)
    }
}