//package com.hzb.erp.configuration;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * @author Ryan
// * description 默认拦截器
// **/
//@Configuration
//@Slf4j
//public class DefaultWebMvcConfigurer implements WebMvcConfigurer {
//
//    @Autowired
//    private SystemConfig systemConfig;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//        log.warn("文件路径"+"file:" + systemConfig.getUploadDir());
//        registry.addResourceHandler("/getfile/**")
//                .addResourceLocations("file:" + systemConfig.getUploadDir());
//
//    }
////    /**
////     * 把自定义拦截器器注册到Spring容器中 并排除路径
////     * @param registry
////     */
////    @Override
////    public void addInterceptors(InterceptorRegistry registry) {
////        // 检查并获取新的token，排除登录接口
//////        registry.addInterceptor(securityHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/app/common/auth/login");
////    }
////
////    @Bean
////    public SecurityHandlerInterceptor securityHandlerInterceptor() {
////        return new SecurityHandlerInterceptor();
////    }
//}
