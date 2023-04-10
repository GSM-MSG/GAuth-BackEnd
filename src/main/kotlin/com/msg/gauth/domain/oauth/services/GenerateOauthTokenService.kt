package com.msg.gauth.domain.oauth.services

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.oauth.OauthRefreshToken
import com.msg.gauth.domain.oauth.exception.ClientSecretMismatchException
import com.msg.gauth.domain.oauth.exception.OauthCodeExpiredException
import com.msg.gauth.domain.oauth.presentation.dto.request.OauthTokenRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.response.OauthTokenResponseDto
import com.msg.gauth.domain.oauth.repository.OauthCodeRepository
import com.msg.gauth.domain.oauth.repository.OauthRefreshTokenRepository
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.ReadOnlyService
import com.msg.gauth.global.security.jwt.OauthTokenProvider
import org.springframework.data.repository.findByIdOrNull

@ReadOnlyService
class GenerateOauthTokenService(
    private val clientRepository: ClientRepository,
    private val userRepository: UserRepository,
    private val oauthTokenProvider: OauthTokenProvider,
    private val refreshTokenRepository: OauthRefreshTokenRepository,
    private val oauthCodeRepository: OauthCodeRepository
){
    fun execute(oauthTokenRequestDto: OauthTokenRequestDto): OauthTokenResponseDto{
        val client = (clientRepository.findByClientIdAndRedirectUri(oauthTokenRequestDto.clientId, oauthTokenRequestDto.redirectUri)
            ?: throw ClientNotFindException())
        if(client.clientSecret != oauthTokenRequestDto.clientSecret)
            throw ClientSecretMismatchException()
        val oauthCode = oauthCodeRepository.findByIdOrNull(oauthTokenRequestDto.code)
            ?: throw OauthCodeExpiredException()
        val email = oauthCode
            .email
        oauthCodeRepository.delete(oauthCode)
        val user = (userRepository.findByEmail(email)
            ?: throw UserNotFoundException())
        return tokenResponseDto(email, client, user)
    }

    private fun tokenResponseDto(
        email: String,
        client: Client,
        user: User
    ): OauthTokenResponseDto {
        val (accessToken, refreshToken) = oauthTokenProvider.run {
            generateOauthAccessToken(email, client.clientId) to generateOauthRefreshToken(email, client.clientId)
        }
        refreshTokenRepository.save(OauthRefreshToken(user.id, refreshToken))
        return OauthTokenResponseDto(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}