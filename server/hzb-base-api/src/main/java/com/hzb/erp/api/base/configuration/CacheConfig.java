package com.hzb.erp.api.base.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> 缓存配置 </p>
 * @author Ryan 541720500@qq.com
 */
@Configuration
@EnableCaching
@ConditionalOnExpression("${system.isSaas:false}==false")
public class CacheConfig extends CachingConfigurerSupport {

    /**
     * 自定义缓存key生成器
     * 加上@Override这个之后才能把它设置为默认的key生成器
     */
    @Bean
    @Primary
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                String re = String.format("%s::%s(%s)", target.getClass().getName(), method.getName(),
                        CollectionUtils.arrayToList(params));//Arrays.asList(params)
                return re;
            }
        };
    }

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 自定义cacheManager
     */
    @Bean
    @Primary
    @Override
    public CacheManager cacheManager() {
        // 设置缓存有效期一小时
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1));
        Map<String, RedisCacheConfiguration> initialCacheConfigurations = new HashMap<>();
//        initialCacheConfigurations.put("sixHoursCache", RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofHours(6)));
//        initialCacheConfigurations.put("minuteCache", RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofMinutes(1)));
        RedisCacheManager redisCacheManager = new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                redisCacheConfiguration,
                initialCacheConfigurations,
                true);
        return redisCacheManager;
    }

}

