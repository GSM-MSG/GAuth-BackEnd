package com.msg.gauth.domain.client

import com.msg.gauth.domain.client.presentation.dto.request.ClientUpdateReqDto
import com.msg.gauth.domain.user.User
import com.msg.gauth.global.entity.BaseIdEntity
import javax.persistence.*

@Entity
class Client(
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
    val serviceName: String,
    val serviceUri: String,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val createdBy: User
): BaseIdEntity(){
    fun updateClient(clientUpdateReqDto: ClientUpdateReqDto): Client{
        val client = Client(
            clientId = this.clientId,
            clientSecret = this.clientSecret,
            redirectUri = clientUpdateReqDto.redirectUri,
            serviceUri = clientUpdateReqDto.serviceUri,
            serviceName = clientUpdateReqDto.serviceName,
            createdBy = this.createdBy
        )
        client.id=this.id
        return client
    }
}