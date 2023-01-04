package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.user.presentation.dto.response.MyProfileResDto
import com.msg.gauth.domain.user.utils.UserUtil
import com.msg.gauth.global.annotation.service.ReadOnlyService

@ReadOnlyService
class MyProfileService(
    private val userUtil: UserUtil,
    private val clientRepository: ClientRepository
) {
    fun execute(): MyProfileResDto {
        val currentUser = userUtil.fetchCurrentUser()
        val clientList = clientRepository.findAllByCreatedBy(currentUser)
            .map { MyProfileResDto.SingleClientResDto(it) }
        return MyProfileResDto(currentUser, clientList)
    }
}