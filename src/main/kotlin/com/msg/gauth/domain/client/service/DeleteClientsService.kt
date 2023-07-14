package com.msg.gauth.domain.client.service

import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@TransactionalService
class DeleteClientsService(
    private val clientRepository: ClientRepository,
    private val userUtil: UserUtil
) {
    fun execute(ids: List<Long>) {
        val user = userUtil.fetchCurrentUser()
        clientRepository.deleteAllByIdsAndCreatedBy(ids, user)
    }
}