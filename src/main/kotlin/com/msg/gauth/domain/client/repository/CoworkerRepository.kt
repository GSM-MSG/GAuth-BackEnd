package com.msg.gauth.domain.client.repository

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.Coworker
import com.msg.gauth.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface CoworkerRepository: JpaRepository<Coworker, Long> {
    fun existsByUserAndClient(user: User, client: Client): Boolean
}