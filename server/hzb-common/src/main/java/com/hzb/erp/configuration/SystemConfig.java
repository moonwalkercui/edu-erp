package com.hzb.erp.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ryan 541720500@qq.com
 * description
 */

@Data
@Component
@ConfigurationProperties(prefix = "system")
public class SystemConfig {

    private Boolean isSaas;
    private Boolean isDemo;
    private String version;
    private String domain;
    private String staticDir;
    private String logDir;
    private String uploadDir;
    private String uploadFileUrlPrefix;
    private Long uploadImgMaxSize;

    private List<String> allowOrigin;
    private List<String> securityIgnoringMatchers;
    private List<String> blackList;

    /**
     * 下面参数是filter里用的，用@Autowaire注解会是null，所以这里get设成静态的, set不是静态
     */

    private static Long jwtTokenRefreshSec;
    private static Long jwtExpiredTtlSec;

    public static Long getJwtTokenRefreshSec() {
        return jwtTokenRefreshSec;
    }

    public void setJwtTokenRefreshSec(Long jwtTokenRefreshSec) {
        SystemConfig.jwtTokenRefreshSec = jwtTokenRefreshSec;
    }

    public static Long getJwtExpiredTtlSec() {
        return jwtExpiredTtlSec;
    }

    public void setJwtExpiredTtlSec(Long jwtExpiredTtlSec) {
        SystemConfig.jwtExpiredTtlSec = jwtExpiredTtlSec;
    }
}
