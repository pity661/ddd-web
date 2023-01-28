package com.wenky.provider.framework.disruptor.service.impl;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import com.wenky.provider.framework.disruptor.model.MessageModel;
import com.wenky.provider.framework.disruptor.service.WorkPoolMqService;
import java.util.concurrent.CountDownLatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-01-17 11:22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkPoolMqServiceImpl implements WorkPoolMqService {

    private final RingBuffer<MessageModel> multiConsumerRingBuffer;

    @Override
    public void sayHelloMq(String message, CountDownLatch countDownLatch) {
        EventTranslator<MessageModel> eventTranslator =
                (event, sequence) -> {
                    event.setMessage(message);
                    event.setCountDownLatch(countDownLatch);
                    log.info(
                            "往消息队列中添加消息：event【{}】, sequence【{}】, Thread 【{}】",
                            event,
                            sequence,
                            Thread.currentThread().getName());
                };
        multiConsumerRingBuffer.publishEvent(eventTranslator);

        // publish时添加指定参数
        //        EventTranslatorOneArg<MessageModel, String> eventTranslatorOneArg =
        //                (event, sequence, value) -> {
        //                    eventTranslator.translateTo(event, sequence);
        //                    System.out.println(value);
        //                };
        //        multiConsumerRingBuffer.publishEvent(
        //                eventTranslatorOneArg,
        //                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd
        // HHmmss")));
    }
}
