package com.msg.gauth.domain.client.persentation.dto.response

import com.msg.gauth.domain.client.Client

data class ClientOneResDto(
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
    val serviceName: String,
    val serviceUri: String,
){
    constructor(client: Client): this(
        clientId = client.clientId,
        clientSecret = client.clientSecret,
        redirectUri = client.redirectUri,
        serviceName = client.serviceName,
        serviceUri = client.serviceUri,
    )
}