package com.msg.gauth.domain.client.repository

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface ClientRepository: JpaRepository<Client, Long> {
    fun findAllByCreatedBy(createdBy: User): List<Client>
    fun findByIdAndCreatedBy(clientId: Long, createdBy: User): Client?
}