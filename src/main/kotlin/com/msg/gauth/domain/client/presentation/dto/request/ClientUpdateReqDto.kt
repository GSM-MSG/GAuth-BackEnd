package com.msg.gauth.domain.client.presentation.dto.request

import com.msg.gauth.domain.client.Client
import javax.validation.constraints.Size

data class ClientUpdateReqDto(
    @field:Size(min = 1, max = 40)
    val serviceName: String,

    @field:Size(min = 1, max = 254)
    val serviceUri: String,

    @field:Size(min = 1, max = 254)
    val redirectUri: String,
){

    fun toEntity(client: Client): Client =
        Client(
            id = client.id,
            clientId = client.clientId,
            clientSecret = client.clientSecret,
            redirectUri = this.redirectUri,
            serviceName = this.serviceName,
            serviceUri = this.serviceUri,
            createdBy = client.createdBy
        )
}