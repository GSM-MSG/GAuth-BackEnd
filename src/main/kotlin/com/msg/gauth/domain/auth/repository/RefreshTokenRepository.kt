package com.msg.gauth.domain.auth.repository

import com.msg.gauth.global.util.count.auth.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, Long> {

    fun findByToken(token: String): RefreshToken?
}