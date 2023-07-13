package com.wenky.provider.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 克林
 * @date 2023/7/13 14:32
 */
@Data
@ConfigurationProperties(prefix = "async")
public class AsyncConfigProperties {

    /** Set the ThreadPoolExecutor's core pool size. Default is 10. */
    private int corePoolSize = 10;

    /** Set the ThreadPoolExecutor's maximum pool size. Default is 500. */
    private int maxPoolSize = 500;

    /** Set the capacity for the ThreadPoolExecutor's BlockingQueue. Default is 10000. */
    private int queueCapacity = 10000;

    /** Set the name prefix for thread. Default is 'ddd-web-async-executor-'. */
    private String threadNamePrefix = "ddd-web-async-executor-";

}
