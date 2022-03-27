package com.hzb.erp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.context.request.RequestContextListener;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Ryan 541720500@qq.com
 */
@SpringBootApplication
@EnableAsync
@Slf4j
public class ErpApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(ErpApplication.class, args);
        Environment env = application.getEnvironment();
        String host = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String version = env.getProperty("system.version");
        log.info("\n--------------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local   : \thttp://localhost:{}\n\t" +
                        "External: \thttp://{}:{}\n\t" +
                        "ApiDoc  : \thttp://{}:{}/app/doc.html#/home\n\t" +
                        "Version : \t{}\n" +
                        "--------------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                host, port,
                host, port,
                version);
    }

    /**
     * 监听器：监听HTTP请求事件
     * 解决RequestContextHolder.getRequestAttributes()空指针问题
     */
    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

}
