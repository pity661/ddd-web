package com.wenky.provider.framework.disruptor.service;

import java.util.concurrent.CountDownLatch;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-01-17 11:22
 */
public interface WorkPoolMqService {
    void sayHelloMq(String message, CountDownLatch countDownLatch);
}
