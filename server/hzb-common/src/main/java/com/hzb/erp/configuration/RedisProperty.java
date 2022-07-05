package com.hzb.erp.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperty {

    private String host;

    private Integer database;

    private Integer port;

    private String password;
}

