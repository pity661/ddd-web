package com.wenky.provider.framework.disruptor.model;

import java.util.concurrent.CountDownLatch;
import lombok.Data;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-01-12 15:55
 */
@Data
public class MessageModel {
    private String message;
    private CountDownLatch countDownLatch;
}
