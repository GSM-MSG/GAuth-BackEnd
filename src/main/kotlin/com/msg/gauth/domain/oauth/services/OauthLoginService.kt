package com.msg.gauth.domain.oauth.services

import com.msg.gauth.domain.auth.exception.PasswordMismatchException
import com.msg.gauth.domain.oauth.presentation.dto.request.OauthLoginRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.response.OauthLoginResponseDto
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class OauthLoginService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val redisTemplate: RedisTemplate<String, String>,
){
    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    fun execute(oauthLoginRequestDto: OauthLoginRequestDto): OauthLoginResponseDto {
        val valueOperation = redisTemplate.opsForValue()
        val user = userRepository.findByEmail(oauthLoginRequestDto.email) ?: throw UserNotFoundException()
        if (passwordEncoder.matches(oauthLoginRequestDto.password, user.password)) {
            throw PasswordMismatchException()
        }
        val code = UUID.randomUUID().toString().split(".")[0]
        valueOperation.set(code, user.email, 1000L * 60 * 5)
        return OauthLoginResponseDto(
            code = code,
        )
    }
}