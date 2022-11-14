package com.msg.gauth.domain.oauth.services

import com.msg.gauth.domain.auth.exception.ExpiredRefreshTokenException
import com.msg.gauth.domain.auth.exception.InvalidRefreshTokenException
import com.msg.gauth.domain.oauth.OauthRefreshToken
import com.msg.gauth.domain.oauth.presentation.dto.response.UserTokenResponseDto
import com.msg.gauth.domain.oauth.repository.OauthRefreshTokenRepository
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.security.jwt.JwtTokenProvider
import org.springframework.stereotype.Service

@Service
class OauthRefreshService(
    private val tokenRepository: OauthRefreshTokenRepository,
    private val tokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
){
    fun execute(refreshToken: String): UserTokenResponseDto{
        val refreshToken = tokenProvider.parseToken(refreshToken) ?: throw InvalidRefreshTokenException()
        val email = tokenProvider.exactEmailFromRefreshToken(refreshToken)
        val user = userRepository.findByEmail(email) ?: throw UserNotFoundException()
        if(!tokenRepository.existsById(user.id))
            throw ExpiredRefreshTokenException()
        val access = tokenProvider.generateAccessToken(email)
        val refresh = tokenProvider.generateRefreshToken(email)
        val expiresAt = tokenProvider.accessExpiredTime
        val newRefreshToken = OauthRefreshToken(
            userId = user.id,
            token = refresh,
        )
        tokenRepository.save(newRefreshToken)
        return UserTokenResponseDto(access, refresh, expiresAt)
    }
}