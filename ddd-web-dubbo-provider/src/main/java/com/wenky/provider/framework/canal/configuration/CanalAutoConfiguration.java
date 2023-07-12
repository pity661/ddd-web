package com.wenky.provider.framework.canal.configuration;

import com.wenky.provider.framework.canal.CanalClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author 克林
 * @date 2023/7/12 14:16
 */
@Configuration
@ConditionalOnProperty(value = "spring.canal.enable", havingValue = "true")
@Import({CanalAsyncConfig.class, CanalProperties.class, CanalClient.class})
public class CanalAutoConfiguration {}
