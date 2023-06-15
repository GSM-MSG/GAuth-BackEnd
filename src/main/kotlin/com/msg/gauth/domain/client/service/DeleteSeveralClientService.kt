package com.msg.gauth.domain.client.service

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.util.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class DeleteSeveralClientService(
    private val clientRepository: ClientRepository,
    private val userUtil: UserUtil
) {
    fun execute(ids: List<Long>) {
        val user = userUtil.fetchCurrentUser()
        ids.map { clientRepository.deleteByIdAndCreatedBy(it, user) }
    }
}