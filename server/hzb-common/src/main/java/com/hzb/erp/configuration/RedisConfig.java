package com.hzb.erp.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * @author Ryan 541720500@qq.com
 * description
 */

@AllArgsConstructor
@Configuration
public class RedisConfig {
    private final RedisProperty redisProperty;

    @Bean
    public JedisConnectionFactory initRedisConnectionFactory() {

        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();
        rsc.setHostName(redisProperty.getHost());
        rsc.setDatabase(redisProperty.getDatabase());
        rsc.setPassword(RedisPassword.of(redisProperty.getPassword()));
        rsc.setPort(redisProperty.getPort());
        return new JedisConnectionFactory(rsc);
    }

    @Bean
    public RedisTemplate<String, Object> initRedisTemplate() {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(initRedisConnectionFactory());

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }

}

