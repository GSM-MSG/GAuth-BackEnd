package com.msg.gauth.domain.oauth.services

import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.oauth.OauthRefreshToken
import com.msg.gauth.domain.oauth.exception.ClientSecretMismatchException
import com.msg.gauth.domain.oauth.exception.OauthCodeExpiredException
import com.msg.gauth.domain.oauth.presentation.dto.request.UserTokenRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.response.UserTokenResponseDto
import com.msg.gauth.domain.oauth.repository.OauthRefreshTokenRepository
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.security.jwt.JwtTokenProvider
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OauthTokenService(
    val clientRepository: ClientRepository,
    val userRepository: UserRepository,
    val tokenProvider: JwtTokenProvider,
    val redisTemplate: RedisTemplate<String, String>,
    val refreshTokenRepository: OauthRefreshTokenRepository,
){
    @Transactional(rollbackFor = [Exception::class], readOnly = true)
    fun execute(userTokenRequestDto: UserTokenRequestDto): UserTokenResponseDto{
        val client = (clientRepository.findByClientId(userTokenRequestDto.clientId)
            ?: throw ClientNotFindException())
        if(client.clientSecret != userTokenRequestDto.clientSecret)
            throw ClientSecretMismatchException()
        val valueOperation = redisTemplate.opsForValue()
        val data = (valueOperation.get(userTokenRequestDto.code)?: throw OauthCodeExpiredException())
        val email = data.substring(data.length - 16)
        val user = (userRepository.findByEmail(email)
            ?: throw UserNotFoundException())
        val accessToken = tokenProvider.generateAccessToken(email)
        val refreshToken = tokenProvider.generateRefreshToken(email)
        val expiresAt = tokenProvider.accessExpiredTime
        refreshTokenRepository.save(OauthRefreshToken(user.id, refreshToken))
        return UserTokenResponseDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresAt = expiresAt
        )
    }
}