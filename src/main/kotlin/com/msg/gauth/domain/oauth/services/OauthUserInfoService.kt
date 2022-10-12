package com.msg.gauth.domain.oauth.services

import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.oauth.exception.ClientSecretMismatchException
import com.msg.gauth.domain.oauth.exception.OauthCodeExpiredException
import com.msg.gauth.domain.oauth.presentation.dto.request.UserInfoRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.response.UserInfoResponseDto
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.exception.exceptions.BasicException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OauthUserInfoService(
    private val redisTemplate: RedisTemplate<String, String>,
    private val clientRepository: ClientRepository,
    private val userRepository: UserRepository,
){
    @Transactional(readOnly = true)
    fun execute(userInfoRequestDto: UserInfoRequestDto): UserInfoResponseDto{
        val valueOperation = redisTemplate.opsForValue()
        val client = clientRepository.findByClientId(userInfoRequestDto.clientId) ?: throw ClientNotFindException()
        if(client.clientSecret != userInfoRequestDto.clientSecret)
            throw ClientSecretMismatchException()
        val email = valueOperation.get(userInfoRequestDto.code) ?: throw OauthCodeExpiredException()
        val user = userRepository.findByEmail(email) ?: throw UserNotFoundException()
        return UserInfoResponseDto(
            email = user.email,
            grade = user.grade,
            classNum = user.classNum,
            num = user.num,
            gender = user.gender
        )
    }
}