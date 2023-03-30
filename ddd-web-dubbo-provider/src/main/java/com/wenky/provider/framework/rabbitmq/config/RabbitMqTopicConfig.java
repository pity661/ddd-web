package com.wenky.provider.framework.rabbitmq.config;

import com.wenky.provider.framework.rabbitmq.RabbitMqQueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: ddd-web
 * @description: topic交换机，绑定的队列，可根据规则匹配routingKey *单个单词，#多个单词
 * @author: wenky
 * @create: 2023-03-30 16:17
 */
@Configuration
public class RabbitMqTopicConfig {
    RabbitMqQueueEnum topicConfig1 = RabbitMqQueueEnum.RABBITMQ_TOPIC_EXCHANGE_QUEUE;
    RabbitMqQueueEnum topicConfig2 = RabbitMqQueueEnum.RABBITMQ_TOPIC_EXCHANGE_SECOND_QUEUE;

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicConfig1.getExchange());
    }

    @Bean
    public Queue topicQueue1() {
        return new Queue(topicConfig1.getQueue());
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(topicConfig2.getQueue());
    }

    @Bean
    public Binding topicBind1() {
        return BindingBuilder.bind(topicQueue1())
                .to(topicExchange())
                .with(topicConfig1.getRoutingKey());
    }

    @Bean
    public Binding topicBind2() {
        return BindingBuilder.bind(topicQueue2())
                .to(topicExchange())
                .with(topicConfig2.getRoutingKey());
    }
}
