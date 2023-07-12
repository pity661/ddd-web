package com.wenky.provider.framework.rabbitmq.configuration;

import com.wenky.provider.framework.rabbitmq.RabbitMqQueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: ddd-web
 * @description: direct交换机，绑定的队列，需精确匹配routingKey
 * @author: wenky
 * @create: 2023-03-30 14:41
 */
@Configuration
@ConditionalOnBean(RabbitMqConfig.class)
public class RabbitMqDirectConfig {

    RabbitMqQueueEnum directConfig = RabbitMqQueueEnum.RABBITMQ_DIRECT_EXCHANGE_QUEUE;
    RabbitMqQueueEnum directDeadConfig = RabbitMqQueueEnum.RABBITMQ_DIRECT_EXCHANGE_DEAD_QUEUE;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directConfig.getExchange());
    }

    @Bean
    public DirectExchange directDeadExchange() {
        return new DirectExchange(directDeadConfig.getExchange());
    }

    // 如果要改配置需要先把原队列删除重新创建
    @Bean
    public Queue directQueue() {
        return QueueBuilder.durable(directConfig.getQueue())
                // ddd_web_direct_queue长度超过1000，进入死信队列
                .maxLength(1000)
                // 消息进入多列10秒为被消费进入死信队列
                .ttl(30000)
                .deadLetterExchange(directDeadConfig.getExchange())
                .deadLetterRoutingKey(directDeadConfig.getRoutingKey())
                // 设置队列消息最大优先值
                .maxPriority(10)
                //                .deliveryLimit(3)   // 表示消息只能被传给三个消费者
                // 集群模式下的quonum队列
                //                .quorum()
                .build();
    }

    @Bean
    public Binding directBind() {
        return BindingBuilder.bind(directQueue())
                .to(directExchange())
                .with(directConfig.getRoutingKey());
    }

    @Bean
    public Queue directDeadQueue() {
        return new Queue(directDeadConfig.getQueue());
    }

    @Bean
    public Binding directDeadBind() {
        return BindingBuilder.bind(directDeadQueue())
                .to(directDeadExchange())
                .with(directDeadConfig.getRoutingKey());
    }
}
