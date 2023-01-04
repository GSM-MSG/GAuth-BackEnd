package com.msg.gauth.domain.client.persentation.dto.request

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.user.User
import javax.validation.constraints.Size

data class ClientRegisterReqDto(
    @field:Size(min = 1, max = 40)
    val serviceName: String,

    @field:Size(min = 1, max = 254)
    val serviceUri: String,

    @field:Size(min = 1, max = 254)
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