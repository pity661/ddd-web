package com.wenky.provider.refresh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-29 09:26
 */
@Data
// 通过配置文件刷新配置，无需添加@RefreshScope注解
@Configuration
@ConfigurationProperties(prefix = "custom")
public class CustomProperties {

    private String name;
    private Integer age;
}
