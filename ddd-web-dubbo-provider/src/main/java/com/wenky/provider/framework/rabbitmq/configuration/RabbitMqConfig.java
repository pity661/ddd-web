package com.wenky.provider.framework.rabbitmq.configuration;

import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-30 14:09
 */
@Slf4j
@RequiredArgsConstructor
public class RabbitMqConfig
        implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private final RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setMandatory(Boolean.TRUE);
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 无论消息是否能正常投送到exchange都会触发该回调
     *
     * @param correlationData
     * @param ack 正常时为true，异常时为false
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info(
                String.format(
                        "【RabbitMq回调触发by-exchange】correlationData::id: %s, ack: %s, cause: %s",
                        correlationData.getId(), ack, cause));
    }

    /**
     * 当消息能正常投送到exchange，但是不能正常进入queue(如binding被删除)会回调此方法
     *
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(
            Message message, int replyCode, String replyText, String exchange, String routingKey) {
        String messageInfo = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info(
                String.format(
                        "【RabbitMq异常回调触发by-queue】message: %s, replyCode: %s, replyText: %s, exchange: %s, routingKey: %s",
                        messageInfo, replyCode, replyText, exchange, routingKey));
    }
}
