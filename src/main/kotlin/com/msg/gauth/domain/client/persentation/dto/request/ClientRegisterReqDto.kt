package com.msg.gauth.domain.client.persentation.dto.request

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.user.User

data class ClientRegisterReqDto(
    val serviceName: String,
    val serviceUri: String,
    val redirectUri: String,
){
    fun toEntity(user: User, clientSecret: String, clientId: String): Client =
        Client(
            clientId = clientId,
            clientSecret = clientSecret,
            serviceName = serviceName,
            serviceUri = serviceUri,
            redirectUri = redirectUri,
            createdBy = user,
        )
}