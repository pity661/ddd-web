package com.wenky.provider.framework.rabbitmq;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-30 14:22
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqConsumer {

    private final RabbitMqProvider rabbitMqProvider;
    @Autowired @Lazy private RabbitMqConsumer rabbitMqConsumer;

    @RabbitListener(queues = "ddd_web.direct.queue")
    public void direct(String content, Channel channel, Message message) throws IOException {
        handle(content, channel, message, RabbitMqQueueEnum.RABBITMQ_DIRECT_EXCHANGE_QUEUE);
    }

    @RabbitListener(queues = "ddd_web.topic.queue")
    public void topic(String content, Channel channel, Message message) throws IOException {
        handle(content, channel, message, RabbitMqQueueEnum.RABBITMQ_TOPIC_EXCHANGE_QUEUE);
    }

    @RabbitListener(queues = "ddd_web.topic.second.queue")
    public void topicSecond(String content, Channel channel, Message message) throws IOException {
        handle(content, channel, message, RabbitMqQueueEnum.RABBITMQ_TOPIC_EXCHANGE_SECOND_QUEUE);
    }

    @RabbitListener(queues = "ddd_web.fanout.queue")
    public void fanoutSecond(String content, Channel channel, Message message) throws IOException {
        handle(content, channel, message, RabbitMqQueueEnum.RABBITMQ_FANOUT_EXCHANGE_QUEUE);
    }

    private void handle(
            String content, Channel channel, Message message, RabbitMqQueueEnum queueConfig)
            throws IOException {
        Long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            rabbitMqConsumer.handle(queueConfig, content);
            channel.basicAck(deliveryTag, Boolean.FALSE);
            //            channel.basicReject(deliveryTag, Boolean.TRUE);
        } catch (Exception e) {
            log.error(String.format("【RabbitMq消费者】发生异常, queue: %s", queueConfig.getQueue()), e);
            // 当requeue为false时，消息会直接进入配置的死信队列,如果未配置死信队列消息就丢失了
            if (maxRetryTimesToDeadQueue(queueConfig, message)) {
                // 将消息手动推入死信队列
                rabbitMqProvider.sendMsg(
                        RabbitMqQueueEnum.RABBITMQ_DIRECT_EXCHANGE_DEAD_QUEUE, content);
                channel.basicAck(deliveryTag, Boolean.FALSE);
                return;
            }
            channel.basicNack(deliveryTag, Boolean.FALSE, Boolean.TRUE);
        }
    }

    private Boolean maxRetryTimesToDeadQueue(RabbitMqQueueEnum queueConfig, Message message) {
        // TODO 将correlationDataId作为key存入redis，同时记录失败次数，
        //  当超过配置次数时手动将消息推入死信队列，同时返回true
        String correlationDataId =
                message.getMessageProperties().getHeader("spring_returned_message_correlation");
        // 判断当前消息处理次数是否超过最大值
        if (Boolean.FALSE) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Transactional
    public void handle(RabbitMqQueueEnum queueConfig, String content) {
        log.info(
                String.format(
                        "【RabbitMq消费者】queue: %s, content: %s", queueConfig.getQueue(), content));
    }
}
