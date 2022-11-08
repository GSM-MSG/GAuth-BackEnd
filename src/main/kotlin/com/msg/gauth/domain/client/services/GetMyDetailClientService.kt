package com.msg.gauth.domain.client.services

import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.persentation.dto.response.ClientDetailResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.user.utils.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetMyDetailClientService(
    private val clientRepository: ClientRepository,
    private val userUtil: UserUtil
) {
    fun execute(id: Long): ClientDetailResDto{
        val client = clientRepository.findByIdAndCreatedBy(id, userUtil.fetchCurrentUser()) ?: throw ClientNotFindException()
        return ClientDetailResDto(client)
    }
}