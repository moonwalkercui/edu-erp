package com.hzb.erp.api.base.configuration;

import com.hzb.erp.configuration.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MyWebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private SystemConfig systemConfig;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 解决swagger-ui.html 404无法访问的问题
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        // 静态资源目录 : 这个策略不使用了，采用直接上传到前端根目录的方式解决静态资源的访问问题
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations("file:" + systemConfig.getStaticDir());
    }

}