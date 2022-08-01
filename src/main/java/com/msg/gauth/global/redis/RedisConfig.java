package com.msg.gauth.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    private final RedisProperties redisProperties;

    @Bean
    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }

    @Bean
    ZSetOperations<String, Object> zSetOperation() {
        return redisTemplate().opsForZSet();
    }
}
