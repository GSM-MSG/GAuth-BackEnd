package com.msg.gauth.domain.auth.services

import com.msg.gauth.domain.auth.RefreshToken
import com.msg.gauth.domain.auth.exception.PasswordMismatchException
import com.msg.gauth.domain.auth.presentation.dto.request.SigninRequestDto
import com.msg.gauth.domain.auth.presentation.dto.response.SigninResponseDto
import com.msg.gauth.domain.auth.repository.RefreshTokenRepository
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.logger.log4k
import com.msg.gauth.global.security.jwt.JwtTokenProvider
import org.slf4j.Logger
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.log

@Service
class SignInService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @log4k
    var logger: Logger? = null

    @Transactional
    fun execute(dto: SigninRequestDto): SigninResponseDto {
        logger?.info("a")
        println("1")
        val user: User = userRepository.findByEmail(dto.email) ?: throw UserNotFoundException()
        if (!passwordEncoder.matches(dto.password, user.password))
            throw PasswordMismatchException()
        val access = jwtTokenProvider.generateAccessToken(dto.email)
        println("2")
        val refresh = jwtTokenProvider.generateRefreshToken(dto.email)
        println("3")
        val expiresAt = jwtTokenProvider.accessExpiredTime
        refreshTokenRepository.save(RefreshToken(user.id, refresh, JwtTokenProvider.REFRESH_EXP))
        return SigninResponseDto(access, refresh, expiresAt)
    }
}