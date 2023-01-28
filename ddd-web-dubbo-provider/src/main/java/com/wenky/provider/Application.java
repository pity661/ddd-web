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
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
