package com.foodtosave.restaurante.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

@TestConfiguration
public class RedisTestConfiguration {
    private final RedisServer redisServer;

    public RedisTestConfiguration(RedisProperties redisProperties) {
        this.redisServer = new RedisServer();
    }

    @PostConstruct
    public void startRedisServer() {
        redisServer.start();
    }

    @PreDestroy
    public void stopRedisServer() {
        redisServer.stop();
    }
}