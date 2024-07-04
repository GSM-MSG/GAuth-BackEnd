package com.msg.gauth.global.redis

import com.msg.gauth.global.redis.properties.RedisProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ZSetOperations
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer

@Configuration
class RedisConfig(
    private val redisProperties: RedisProperties
) {

    @Bean
    @ConditionalOnMissingBean(RedisConnectionFactory::class)
    fun redisConnectionFactory(): RedisConnectionFactory =
        LettuceConnectionFactory(redisProperties.host, redisProperties.port)

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> =
        RedisTemplate<String, Any>().apply {
            this.setConnectionFactory(redisConnectionFactory())
            this.keySerializer = RedisSerializer.string()
            this.valueSerializer = GenericJackson2JsonRedisSerializer()
        }

    @Bean
    fun zSetOperation(): ZSetOperations<String, Any> =
        redisTemplate().opsForZSet()
}