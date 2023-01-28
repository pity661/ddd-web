package com.wenky.provider.framework.disruptor.service.impl;

import com.wenky.provider.Application;
import com.wenky.provider.framework.disruptor.service.EventHandlerMqService;
import com.wenky.provider.framework.disruptor.service.WorkPoolMqService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DisruptorServiceImplTest {

    @Autowired private EventHandlerMqService disruptorMqService;
    @Autowired private WorkPoolMqService workPoolMqService;

    /**
     * 项目内部使用Disruptor做消息队列
     *
     * @throws Exception
     */
    @Test
    public void sayHelloMqTest() throws Exception {
        log.info("生产者开始生产消息");
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch countDownLatch = new CountDownLatch(1000000);
        for (int i = 0; i < 1000000; i++) {
            int finalI = i;
            // 多线程模拟多个生产者
            executorService.submit(
                    () ->
                            disruptorMqService.sayHelloMq(
                                    String.format(
                                            "%s: %d Hello world!",
                                            Thread.currentThread().getName(), finalI),
                                    countDownLatch));
        }
        log.info("生产者消息生产完毕");
        executorService.shutdown();
        countDownLatch.await();
    }
}
