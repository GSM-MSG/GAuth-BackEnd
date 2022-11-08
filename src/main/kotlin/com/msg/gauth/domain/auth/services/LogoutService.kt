package com.msg.gauth.domain.auth.services

import com.msg.gauth.domain.auth.repository.RefreshTokenRepository
import com.msg.gauth.domain.user.utils.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LogoutService(
    private val userUtil: UserUtil,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    @Transactional(rollbackFor = [Exception::class])
    fun execute() {
        val currentUser = userUtil.fetchCurrentUser()
        refreshTokenRepository.findByUserId(currentUser.id)?.let {
            refreshTokenRepository.delete(it)
        }
    }
}