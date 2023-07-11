package com.wenky.provider.framework.rabbitmq;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-30 14:19
 */
@Slf4j
@Configuration
@ConditionalOnBean(RabbitAutoConfiguration.class)
@RequiredArgsConstructor
public class RabbitMqProvider {

    private final RabbitTemplate rabbitTemplate;

    public void sendMsg(RabbitMqQueueEnum config, String content) {
        sendMsg(config.getExchange(), config.getRoutingKey(), config.getExpiration(), content);
    }

    public void sendMsg(String exchange, String routingKey, String expiration, String content) {
        String correlationDataId = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(correlationDataId);
        // 设置消息延迟时间
        MessageProperties properties = new MessageProperties();
        properties.setExpiration(expiration);
        // 根据消息类型和业务情况设置优先级
        properties.setPriority(5);
        Message message = new Message(content.getBytes(StandardCharsets.UTF_8), properties);
        log.info(
                String.format(
                        "【RabbitMq生产者】消息发送, exchange: %s, routingKey: %s, expiration: %s, content: %s",
                        exchange, routingKey, expiration, content));
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
        log.info(String.format("【RabbitMq生产者】消息发送完成, correlationDataId: %s", correlationDataId));
    }
}
