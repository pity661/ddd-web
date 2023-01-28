package com.wenky.provider.framework.disruptor.handler;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.LifecycleAware;
import com.wenky.provider.framework.disruptor.model.MessageModel;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-01-12 15:58
 */
@Slf4j
public class CustomEventHandler implements EventHandler<MessageModel>, LifecycleAware {

    private String name;

    public CustomEventHandler(String name) {
        this.name = name;
    }

    @SneakyThrows(InterruptedException.class)
    @Override
    public void onEvent(MessageModel event, long sequence, boolean endOfBatch) {
        // 休眠以确定消息消费是异步的
        TimeUnit.SECONDS.sleep(1);
        if (event != null) {
            event.getCountDownLatch().countDown();
            log.info(
                    "消费者{}消费数据, event:{}, sequence:{}, endOfBatch:{}, thread:{}",
                    this.name,
                    event,
                    sequence,
                    endOfBatch,
                    Thread.currentThread().getName());
        }
    }

    // BatchEventProcessor::run
    @Override
    public void onStart() {}

    @Override
    public void onShutdown() {}
}
