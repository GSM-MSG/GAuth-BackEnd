package com.msg.gauth.domain.client.services

import com.msg.gauth.domain.client.persentation.dto.response.SingleClientResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.user.utils.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetMyAllClientsService(
    private val clientRepository: ClientRepository,
    private val userUtil: UserUtil,
) {
    fun execute(): List<SingleClientResDto> =
        clientRepository.findAllByCreatedBy(userUtil.fetchCurrentUser())
            .map { SingleClientResDto(it) }
}