package com.wenky.provider.framework.disruptor.service.impl;

import com.lmax.disruptor.RingBuffer;
import com.wenky.provider.framework.disruptor.model.MessageModel;
import com.wenky.provider.framework.disruptor.service.EventHandlerMqService;
import java.util.concurrent.CountDownLatch;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-01-12 16:26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EventHandlerMqServiceImpl implements EventHandlerMqService {

    private final RingBuffer<MessageModel> messageModelRingBuffer;

    @SneakyThrows
    @Override
    public void sayHelloMq(String message, CountDownLatch countDownLatch) {
        // 保证线程池初始化
        //        TimeUnit.MILLISECONDS.sleep(10);

        // 获取下一个Event槽的下标
        // SingleProducerSequencerPad::next 135 gatingSequences
        // 当前消费者消费位置,避免前置任务被覆盖
        // UNSAFE.putLongVolatile   支持volatile写内存语义，保证更新对所有线程立即可见
        long sequence = messageModelRingBuffer.next();
        try {
            // 给Event填充数据
            MessageModel event = messageModelRingBuffer.get(sequence);
            event.setMessage(message);
            event.setCountDownLatch(countDownLatch);
            log.info(
                    "往消息队列中添加消息：event【{}】, sequence【{}】, Thread 【{}】",
                    event,
                    sequence,
                    Thread.currentThread().getName());
        } catch (Exception e) {
            log.error(
                    "failed to add event to messageModelRingBuffer for : e = {},{}",
                    e,
                    e.getMessage());
        } finally {
            // 发布Event，激活观察者去消费，将sequence传递给该消费者
            // 注意最后的publish方法必须放在finally中以确保必须得到调用；
            // 如果某个请求的sequence未被提交将会堵塞后续的发布操作或者其他的producer
            // putOrderedLong 不保证变量值的修改对其他线程立即可见
            messageModelRingBuffer.publish(sequence);
        }
    }
}
