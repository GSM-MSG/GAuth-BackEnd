package com.msg.gauth.domain.auth.repository

import com.msg.gauth.domain.auth.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository: CrudRepository<RefreshToken, Long> {
    fun findByUserId(userId: Long): RefreshToken?
}