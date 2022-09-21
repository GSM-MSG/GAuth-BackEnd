package com.msg.gauth.domain.client.persentation.dto.request

data class ClientUpdateReqDto(
    val serviceName: String,
    val serviceUri: String,
    val redirectUri: String,
)