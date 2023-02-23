package com.msg.gauth.domain.client.presentation.dto.response

import com.msg.gauth.domain.client.Client

data class ClientDetailResDto(
    val id: Long,
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
    val serviceName: String,
    val serviceUri: String,
){
    constructor(client: Client): this(
        id = client.id,
        clientId = client.clientId,
        clientSecret = client.clientSecret,
        redirectUri = client.redirectUri,
        serviceName = client.serviceName,
        serviceUri = client.serviceUri,
    )
}