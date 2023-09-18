package com.msg.gauth.domain.auth.service

import com.msg.gauth.global.util.count.auth.RefreshToken
import com.msg.gauth.domain.auth.exception.ExpiredRefreshTokenException
import com.msg.gauth.domain.auth.exception.InvalidRefreshTokenException
import com.msg.gauth.domain.auth.presentation.dto.response.RefreshResponseDto
import com.msg.gauth.domain.auth.repository.RefreshTokenRepository
import com.msg.gauth.global.annotation.service.TransactionalService
import com.msg.gauth.global.security.jwt.JwtTokenProvider
import com.msg.gauth.global.security.jwt.TokenParser

@TransactionalService
class RefreshService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val tokenParser: TokenParser
) {
    fun execute(requestToken: String): RefreshResponseDto {
        val refreshToken = tokenParser.parseToken(requestToken) ?: throw InvalidRefreshTokenException()
        val email = tokenParser.exactEmailFromRefreshToken(refreshToken)

        val existingRefreshToken = refreshTokenRepository.findByToken(refreshToken)
            ?: throw ExpiredRefreshTokenException()

        if (existingRefreshToken.token != refreshToken)
            throw InvalidRefreshTokenException()

        val (access, refresh) = jwtTokenProvider.run {
            generateAccessToken(email) to generateRefreshToken(email)}

        val newRefreshToken = RefreshToken(
            userId = existingRefreshToken.userId,
            token = refresh
        )

        refreshTokenRepository.save(newRefreshToken)
        return RefreshResponseDto(access, refresh, jwtTokenProvider.accessExpiredTime)
    }
}