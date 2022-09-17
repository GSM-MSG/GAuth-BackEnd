package com.msg.gauth.domain.auth.services

import com.msg.gauth.domain.auth.exception.ExpiredRefreshTokenException
import com.msg.gauth.domain.auth.exception.InvalidRefreshTokenException
import com.msg.gauth.domain.auth.presentation.dto.response.RefreshResponseDto
import com.msg.gauth.domain.auth.repository.RefreshTokenRepository
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RefreshService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    @Transactional
    fun execute(refreshToken: String): RefreshResponseDto {
        val email = jwtTokenProvider.exactEmailFromRefreshToken(refreshToken!!)
        val user: User = userRepository.findByEmail(email) ?: throw UserNotFoundException()
        val redisRefreshToken = refreshTokenRepository.findById(user.id)
            .orElseThrow { ExpiredRefreshTokenException() }
        if (redisRefreshToken.token != refreshToken)
            throw InvalidRefreshTokenException()

        val access = jwtTokenProvider.generateAccessToken(email)
        val refresh = jwtTokenProvider.generateRefreshToken(email)
        val expiresAt = jwtTokenProvider.accessExpiredTime
        redisRefreshToken.updateToken(refresh, JwtTokenProvider.REFRESH_EXP)
        refreshTokenRepository.save(redisRefreshToken)
        return RefreshResponseDto(access, refresh, expiresAt)
    }
}