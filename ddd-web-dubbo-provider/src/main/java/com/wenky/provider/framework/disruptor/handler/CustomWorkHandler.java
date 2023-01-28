package com.wenky.provider.framework.disruptor.handler;

import com.lmax.disruptor.LifecycleAware;
import com.lmax.disruptor.WorkHandler;
import com.wenky.provider.framework.disruptor.model.MessageModel;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-01-17 10:12
 */
@Slf4j
public class CustomWorkHandler implements WorkHandler<MessageModel>, LifecycleAware {

    @SneakyThrows
    @Override
    public void onEvent(MessageModel event) {
        // 休眠以确定消息消费是异步的
        TimeUnit.SECONDS.sleep(1);
        if (event != null) {
            event.getCountDownLatch().countDown();
            log.info("消费者消费数据, event:{}, thread:{}", event, Thread.currentThread().getName());
        }
    }

    // WorkProcessor::run
    @Override
    public void onStart() {
        // do something when start
    }

    @Override
    public void onShutdown() {
        // AlertException
        // do something when shutdown
    }
}
