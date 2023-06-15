package com.msg.gauth.domain.client.repository

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ClientRepository: JpaRepository<Client, Long> {
    fun findAllByCreatedBy(createdBy: User): List<Client>
    fun findByIdAndCreatedBy(clientId: Long, createdBy: User): Client?
    fun findByClientId(clientId: String): Client?
    fun findByClientIdAndRedirectUri(clientId: String, redirectUri: String): Client?
    fun findByServiceNameContaining(serviceName: String, pageable: Pageable): Page<Client>
    fun deleteByIdAndCreatedBy(id: Long, createdBy: User)
}