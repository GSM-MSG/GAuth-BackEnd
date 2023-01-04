package com.msg.gauth.domain.client.persentation.dto.request

import javax.validation.constraints.Size

data class ClientUpdateReqDto(
    @field:Size(min = 1, max = 40)
    val serviceName: String,

    @field:Size(min = 1, max = 254)
    val serviceUri: String,

    @field:Size(min = 1, max = 254)
    val redirectUri: String,
)