package com.msg.gauth.domain.client.persentation.dto.request

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.user.User

class ClientRegisterReqDto(
    private val serviceName: String,
    private val serviceUri: String,
    private val redirectUri: String,
){
    fun toEntity(user: User, clientSecret: String, clientId: String):Client{
        return Client(
            clientId=clientId,
            clientSecret = clientSecret,
            serviceName = serviceName,
            serviceUri = serviceUri,
            redirectUri = redirectUri,
            createdBy = user,
        )
    }
}