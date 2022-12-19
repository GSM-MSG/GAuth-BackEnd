package com.msg.gauth.domain.oauth.services

import com.msg.gauth.domain.auth.exception.PasswordMismatchException
import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.oauth.OauthRefreshToken
import com.msg.gauth.domain.oauth.exception.ClientSecretMismatchException
import com.msg.gauth.domain.oauth.exception.OauthCodeExpiredException
import com.msg.gauth.domain.oauth.presentation.dto.request.OauthLoginReqDto
import com.msg.gauth.domain.oauth.presentation.dto.request.UserTokenRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.response.UserTokenResponseDto
import com.msg.gauth.domain.oauth.repository.OauthCodeRepository
import com.msg.gauth.domain.oauth.repository.OauthRefreshTokenRepository
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OauthTokenService(
    private val clientRepository: ClientRepository,
    private val userRepository: UserRepository,
    private val tokenProvider: JwtTokenProvider,
    private val refreshTokenRepository: OauthRefreshTokenRepository,
    private val oauthCodeRepository: OauthCodeRepository,
    private val passwordEncoder: PasswordEncoder,
){
    @Transactional(rollbackFor = [Exception::class], readOnly = true)
    fun execute(userTokenRequestDto: UserTokenRequestDto): UserTokenResponseDto{
        val client = (clientRepository.findByClientId(userTokenRequestDto.clientId)
            ?: throw ClientNotFindException())
        if(client.clientSecret != userTokenRequestDto.clientSecret)
            throw ClientSecretMismatchException()
        val email = oauthCodeRepository.findById(userTokenRequestDto.code)
            .orElseThrow { throw OauthCodeExpiredException() }
            .email
        val user = (userRepository.findByEmail(email)
            ?: throw UserNotFoundException())
        return tokenResponseDto(email, client, user)
    }

    @Transactional(rollbackFor = [Exception::class], readOnly = true)
    fun execute(oauthLoginReqDto: OauthLoginReqDto): UserTokenResponseDto{
        val client = (clientRepository.findByClientId(oauthLoginReqDto.clientId)
            ?: throw ClientNotFindException())
        val email = oauthLoginReqDto.email
        val user = (userRepository.findByEmail(email)
            ?: throw UserNotFoundException())
        if(!passwordEncoder.matches(oauthLoginReqDto.password, user.password))
            throw PasswordMismatchException()
        return tokenResponseDto(email, client, user)
    }

    private fun tokenResponseDto(
        email: String,
        client: Client,
        user: User
    ): UserTokenResponseDto {
        val accessToken = tokenProvider.generateOauthAccessToken(email, client.clientId)
        val refreshToken = tokenProvider.generateOauthRefreshToken(email, client.clientId)
        val expiresAt = tokenProvider.accessExpiredTime
        refreshTokenRepository.save(OauthRefreshToken(user.id, refreshToken))
        return UserTokenResponseDto(
            accessToken = accessToken,
            refreshToken = refreshToken,
            expiresAt = expiresAt
        )
    }
}