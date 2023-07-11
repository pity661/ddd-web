package com.wenky.provider.controller;

import com.wenky.provider.framework.rabbitmq.RabbitMqProvider;
import com.wenky.provider.framework.rabbitmq.RabbitMqQueueEnum;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-30 14:22
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class RabbitMqController {

    @Autowired @Lazy private RabbitMqProvider rabbitMqProvider;

    // curl -X POST "http://127.0.0.1:8081/provider?content=aa"
    @PostMapping(value = "/provider")
    public String provider(String content, HttpServletRequest request) {
        rabbitMqProvider.sendMsg(RabbitMqQueueEnum.RABBITMQ_DIRECT_EXCHANGE_QUEUE, content);
        return "SUCCESS";
    }

    // curl -X POST "http://127.0.0.1:8081/topic/provider?content=aa"
    @PostMapping(value = "/topic/provider")
    public String topicProvider(String content, HttpServletRequest request) {
        // 当使用 routingKey: ddd_web.#.queue 发送消息时，匹配的routingKey都会投送消息到对应队列
        rabbitMqProvider.sendMsg(
                RabbitMqQueueEnum.RABBITMQ_TOPIC_EXCHANGE_QUEUE.getExchange(),
                RabbitMqQueueEnum.RABBITMQ_TOPIC_EXCHANGE_QUEUE.getQueue(),
                null,
                content);
        return "SUCCESS";
    }

    // curl -X POST "http://127.0.0.1:8081/fanout/provider?content=aa"
    @PostMapping(value = "/fanout/provider")
    public String fanoutProvider(String content, HttpServletRequest request) {
        rabbitMqProvider.sendMsg(
                RabbitMqQueueEnum.RABBITMQ_FANOUT_EXCHANGE_QUEUE.getExchange(),
                null,
                null,
                content);
        return "SUCCESS";
    }
}
