package com.msg.gauth.domain.client

import com.msg.gauth.domain.client.persentation.dto.request.ClientUpdateReqDto
import com.msg.gauth.domain.user.User
import com.msg.gauth.global.entity.BaseIdEntity
import javax.persistence.*

@Entity
class Client(
    val clientId: String,
    val clientSecret: String,
    var redirectUri: String,
    var serviceName: String,
    var serviceUri: String,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val createdBy: User
): BaseIdEntity() {
    fun update(clientUpdateReqDto: ClientUpdateReqDto){
        this.redirectUri = clientUpdateReqDto.redirectUri
        this.serviceName = clientUpdateReqDto.serviceName
        this.serviceUri = clientUpdateReqDto.serviceUri
    }
}