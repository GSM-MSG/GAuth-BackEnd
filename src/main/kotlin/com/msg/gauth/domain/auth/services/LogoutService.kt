package com.msg.gauth.domain.auth.services

import com.msg.gauth.domain.auth.RefreshToken
import com.msg.gauth.domain.auth.repository.RefreshTokenRepository
import com.msg.gauth.domain.user.utils.UserUtil
import org.springframework.stereotype.Service

@Service
class LogoutService(
    private val userUtil: UserUtil,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun execute() {
        val currentUser = userUtil.fetchCurrentUser()
        refreshTokenRepository.findByUserId(currentUser.id)?.let {
            refreshTokenRepository.delete(it)
        }
    }
}