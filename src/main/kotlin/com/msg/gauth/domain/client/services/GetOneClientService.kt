package com.msg.gauth.domain.client.services

import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.persentation.dto.response.ClientDetailResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.global.util.CurrentUserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetOneClientService(
    private val clientRepository: ClientRepository,
    private val currentUserUtil: CurrentUserUtil,
) {
    fun execute(id: Long): ClientDetailResDto{
        val client = clientRepository.findByIdAndCreatedBy(id, currentUserUtil.getCurrentUser()) ?: throw ClientNotFindException()
        return ClientDetailResDto(client)
    }
}