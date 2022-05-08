package com.hzb.erp.base.configuration;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p> Knife4j 配置文件 </p>
 *
 * @author Ryan 541720500@qq.com
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Profile({"dev"})
public class Knife4jConfig {
    @Bean(value = "adminCenterApi")
    public Docket adminCenterApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("管理端接口")
                .useDefaultResponseMessages(false)
                .apiInfo(new ApiInfoBuilder()
                        .description("教务系统API测试文档")
                        .contact(new Contact("大风崔", "https://hzb-it.com", "541720500@qq.com"))
                        .version("vv1.0.1")
                        .title("教务系统API文档")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hzb.erp.adminCenter"))
                .paths(PathSelectors.any())
                .build();

    }

    @Bean(value = "studentCenterApi")
    public Docket studentCenterApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("家长移动端接口")
                .apiInfo(new ApiInfoBuilder()
                        .title("家长移动端 RESTful APIs")
                        .description("家长移动端接口")
                        .termsOfServiceUrl("http://localhost:8999/")
                        .contact(new Contact("大风崔", "https://hzb-it.com", "541720500@qq.com"))
                        .version("v1.0.1")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hzb.erp.studentCenter"))
                .paths(PathSelectors.any())
                .build();
    }


    @Bean(value = "teacherCenterApi")
    public Docket teacherCenterApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("老师移动端接口")
                .apiInfo(new ApiInfoBuilder()
                        .title("老师移动端接口")
                        .description("教室移动端接口")
                        .termsOfServiceUrl("http://localhost:8999/")
                        .contact(new Contact("大风崔", "https://hzb-it.com", "541720500@qq.com"))
                        .version("v1.0.1")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hzb.erp.teacherCenter"))
                .paths(PathSelectors.any())
                .build();
    }


    @Bean(value = "wechatApi")
    public Docket wechatApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("微信公众号交互接口")
                .apiInfo(new ApiInfoBuilder()
                        .title("微信公众号交互接口")
                        .description("微信公众号交互接口")
                        .termsOfServiceUrl("http://localhost:8999/")
                        .contact(new Contact("大风崔", "https://hzb-it.com", "541720500@qq.com"))
                        .version("v1.0.1")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hzb.erp.wechat.controller"))
                .paths(PathSelectors.any())
                .build();
    }


}
