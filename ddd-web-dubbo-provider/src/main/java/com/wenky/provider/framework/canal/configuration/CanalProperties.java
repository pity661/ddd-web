package com.wenky.provider.framework.canal.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 克林
 * @date 2023/7/12 14:25
 */
@Data
@ConfigurationProperties(prefix = "canal")
public class CanalProperties {

    private String host = "127.0.0.1";
    private Integer port = 11111;
    private String destination = "example";
    private String userName;
    private String password;
    private Integer batchSize = 1000;
}
