package com.hzb.erp.security;

import com.hzb.erp.common.configuration.SystemConfig;
import com.hzb.erp.common.constants.CommonConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;
import java.util.List;

/**
 * @author Ryan
 */
@Configuration
public class CorsConfig {

    @Autowired
    private SystemConfig systemConfig;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许访问的客户端域名
        List<String> allowOrigins = systemConfig.getAllowOrigin();
        if (allowOrigins == null || allowOrigins.size() == 0) {
            allowOrigins = Collections.singletonList("*");
        }
        corsConfiguration.setAllowedOriginPatterns(allowOrigins);
        // 允许任何来源
//        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        //2,允许任何请求头
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        //3,允许任何方法
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        //4,允许凭证
        corsConfiguration.setAllowCredentials(true);
        // 允许前端获取请求头
        corsConfiguration.setExposedHeaders(Collections.singletonList(CommonConst.DEFAULT_TOKEN_NAME));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

}

