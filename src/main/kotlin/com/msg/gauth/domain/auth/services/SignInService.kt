package com.msg.gauth.domain.auth.services

import com.msg.gauth.domain.auth.RefreshToken
import com.msg.gauth.domain.auth.exception.PasswordMismatchException
import com.msg.gauth.domain.auth.exception.SignInBanException
import com.msg.gauth.domain.auth.exception.UserIsPendingException
import com.msg.gauth.domain.auth.presentation.dto.request.SigninRequestDto
import com.msg.gauth.domain.auth.presentation.dto.response.SigninResponseDto
import com.msg.gauth.domain.auth.repository.RefreshTokenRepository
import com.msg.gauth.domain.auth.util.TooManyRequestValidUtil
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.TransactionalService
import com.msg.gauth.global.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder

@TransactionalService
class SignInService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tooManyRequestValidUtil: TooManyRequestValidUtil,
) {
    fun execute(dto: SigninRequestDto): SigninResponseDto {
        val user: User = userRepository.findByEmail(dto.email) ?: throw UserNotFoundException()
        if (user.state == UserState.SIGN_IN_BAN)
            throw SignInBanException()
        tooManyRequestValidUtil.validRequest(dto.email)

        if (!passwordEncoder.matches(dto.password, user.password))
            throw PasswordMismatchException()

        if(user.state != UserState.CREATED)
            throw UserIsPendingException()

        val (access, refresh) = jwtTokenProvider.run {
            generateAccessToken(dto.email) to generateRefreshToken(dto.email)}

        val expiresAt = jwtTokenProvider.accessExpiredTime

        refreshTokenRepository.save(RefreshToken(user.id, refresh))
        return SigninResponseDto(access, refresh, expiresAt)
    }
}