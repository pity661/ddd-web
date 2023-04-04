package com.wenky.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    // 服务路由 流量控制 熔断保护 日志监控 安全认证
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
