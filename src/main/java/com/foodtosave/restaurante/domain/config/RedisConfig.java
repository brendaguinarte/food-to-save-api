package com.foodtosave.restaurante.domain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.time.Duration;

@Configuration
    @AutoConfigureAfter(RedisAutoConfiguration.class)
    @EnableCaching
    public class RedisConfig {

        @Value("${spring.cache.redis.time-to-live}")
        private long TTL;

        @Bean
        public RedisTemplate<String, Serializable> redisCacheTemplate(LettuceConnectionFactory conexaoRedis) {
            RedisTemplate<String, Serializable> modelo = new RedisTemplate<>();
            modelo.setKeySerializer(new StringRedisSerializer());
            modelo.setValueSerializer(new GenericJackson2JsonRedisSerializer());
            modelo.setConnectionFactory(conexaoRedis);

            return modelo;
        }

        @Bean
        public CacheManager gerenciadorCacheRedis(RedisConnectionFactory factory) {
            RedisCacheConfiguration configuracao = RedisCacheConfiguration
                    .defaultCacheConfig()
                    .entryTtl(Duration.ofSeconds(TTL))
                    .disableCachingNullValues();
            RedisCacheConfiguration redisCacheConfiguration = configuracao
                    .serializeKeysWith(
                            RedisSerializationContext
                                    .SerializationPair
                                    .fromSerializer(new StringRedisSerializer()))
                    .serializeValuesWith(RedisSerializationContext.SerializationPair
                            .fromSerializer(new GenericJackson2JsonRedisSerializer()));
            return RedisCacheManager
                    .builder(factory)
                    .cacheDefaults(redisCacheConfiguration)
                    .build();
        }


}
