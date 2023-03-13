package com.wenky.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-28 17:55
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
// 指定basePackages
// @EnableJpaRepositories(basePackages = {"com.wenky.provider.dao.repository"})
public class Application {
    // 8081
    // VM options:
    // -Dapp.config.log.file=/Users/huwenqi/Documents/wenky/java/ddd-web/ddd-web-dubbo-provider/application.log -Dproject.name=dubbo-provider -Dcsp.sentinel.dashboard.server=127.0.0.1:8858

    // 8082
    // VM options:
    // -Dapp.config.log.file=/Users/huwenqi/Documents/wenky/java/ddd-web/ddd-web-dubbo-provider/application-8082.log -Dproject.name=dubbo-provider -Dcsp.sentinel.dashboard.server=127.0.0.1:8858
    // environment variables: dubbo.protocol.port=20881;server.port=8082

    // environment variables: SERVER_IP=10.100.19.191
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
