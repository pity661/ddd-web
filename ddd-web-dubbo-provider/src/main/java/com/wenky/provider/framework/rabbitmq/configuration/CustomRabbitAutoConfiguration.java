package com.wenky.provider.framework.rabbitmq.configuration;

import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author 克林
 * @date 2023/7/11 17:05
 */
@Configuration
@ConditionalOnProperty(value = "spring.rabbitmq.enable", havingValue = "true")
@Import(RabbitMqConfig.class)
public class CustomRabbitAutoConfiguration extends RabbitAutoConfiguration {}
