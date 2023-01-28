package com.wenky.provider.framework.disruptor.service;

import java.util.concurrent.CountDownLatch;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-01-12 16:25
 */
public interface EventHandlerMqService {
    void sayHelloMq(String message, CountDownLatch countDownLatch);
}
