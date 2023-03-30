package com.wenky.provider.framework.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-30 14:35
 */
@AllArgsConstructor
public enum RabbitMqQueueEnum {
    RABBITMQ_DIRECT_EXCHANGE_QUEUE(
            "ddd_web_direct_exchange",
            "ddd_web_direct_routingKey",
            "ddd_web.direct.queue",
            null,
            5),
    RABBITMQ_DIRECT_EXCHANGE_DEAD_QUEUE(
            "ddd_web_direct_dead_exchange",
            "ddd_web_direct_dead_routingKey",
            "ddd_web_dead.direct.queue",
            null,
            0),
    RABBITMQ_TOPIC_EXCHANGE_QUEUE(
            "ddd_web_topic_exchange", "ddd_web.*.queue", "ddd_web.topic.queue", null, 5),
    RABBITMQ_TOPIC_EXCHANGE_SECOND_QUEUE(
            "ddd_web_topic_exchange", "ddd_web.#.queue", "ddd_web.topic.second.queue", null, 5),

    RABBITMQ_FANOUT_EXCHANGE_QUEUE("ddd_web_fanout_exchange", "", "ddd_web.fanout.queue", null, 5),
    ;
    @Getter private String exchange;
    @Getter private String routingKey;
    @Getter private String queue;
    // 消息延迟时间，单位毫秒
    @Getter private String expiration;
    @Getter private Integer maxRetryTimes;
}
