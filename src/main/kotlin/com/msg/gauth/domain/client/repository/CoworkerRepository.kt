package com.msg.gauth.domain.client.repository

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.Coworker
import com.msg.gauth.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface CoworkerRepository : JpaRepository<Coworker, Long> {
    fun existsByUserAndClient(user: User, client: Client): Boolean
    fun findByUserAndClient(user: User, client: Client): Coworker?
    fun findByClient(client: Client): List<Coworker>
}