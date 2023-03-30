package com.wenky.provider.framework.rabbitmq.config;

import com.wenky.provider.framework.rabbitmq.RabbitMqQueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: ddd-web
 * @description: 扇形交换机，绑定的队列，无需routingKey
 * @author: wenky
 * @create: 2023-03-30 18:14
 */
@Configuration
public class RabbitMqFanoutConfig {
    RabbitMqQueueEnum fanoutConfig = RabbitMqQueueEnum.RABBITMQ_FANOUT_EXCHANGE_QUEUE;

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutConfig.getExchange());
    }

    @Bean
    public Queue fanoutQueue() {
        return new Queue(fanoutConfig.getQueue());
    }

    @Bean
    public Binding fanoutBind() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBind1(@Qualifier(value = "directQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(fanoutExchange());
    }
}
