package com.wenky.provider.framework.disruptor.configuration;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import com.wenky.provider.framework.disruptor.factory.CustomEventFactory;
import com.wenky.provider.framework.disruptor.handler.CustomEventHandler;
import com.wenky.provider.framework.disruptor.handler.CustomWorkHandler;
import com.wenky.provider.framework.disruptor.model.MessageModel;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-01-12 16:08
 */
@Configuration
public class MQManager {

    // ？？？
    // 1、ProducerType SINGLE和MULTI区别
    // single和multi的概念，在这里应该理解成生产者的线程个数。如果是不同线程生产的应该使用multi，如在controller接口中
    // 2、handleEventsWith 多个不同类型的消费者
    // 3、当队列达到上限，不能继续添加任务逻辑     当生产者速率很快，消费者很慢的时候，直接阻塞
    // BatchEventProcessor::processEvents -> 165
    // 4、系统重启，未处理的任务怎么办
    // 初始化保存任务的时候，先入库持久化保存，根据状态记录消费情况
    @Bean
    public RingBuffer<MessageModel> messageModelRingBuffer() {
        // 这个线程池的目的是启动disruptor，而不是消费者的线程数
        // ExecutorService executorService = Executors.newFixedThreadPool(2);
        CustomEventFactory factory = new CustomEventFactory();
        // 指定ringbuffer字节大小，必须为2的N次方（能将求模运算转为位运算提高效率），否则将影响效率
        // 避免生产者初始化，垃圾回收 缓存初始化对象数量
        int bufferSize = 4;
        // 单线程消费者
        // Disruptor::shutdown 当所有任务都被消费时，hasBacklog返回false，循环结束
        Disruptor<MessageModel> disruptor =
                new Disruptor<>(
                        factory,
                        bufferSize,
                        Executors.defaultThreadFactory(),
                        // executorService,
                        ProducerType.MULTI, // SINGLE单个生产者，MULTI多个生产者 利用cas支持多线程
                        new BlockingWaitStrategy());

        EventHandlerGroup<MessageModel> eventHandlerGroupA =
                disruptor.handleEventsWith(new CustomEventHandler("A"));
        // 按顺序先执行上面的，再执行下面的 内部调用的是Disruptor::createEventProcessors
        EventHandlerGroup<MessageModel> eventHandlerGroupB =
                eventHandlerGroupA.then(new CustomEventHandler("B"));
        EventHandlerGroup<MessageModel> eventHandlerGroupC =
                eventHandlerGroupB.then(new CustomEventHandler("C"));
        // 多线程消费者。多个同类型的消费者一起消费，乱序消费，不区分先后顺序
        int consumerCount = 3;
        EventHandlerGroup<MessageModel> eventHandlerGroupD =
                eventHandlerGroupC.handleEventsWithWorkerPool(
                        IntStream.range(0, consumerCount)
                                .boxed()
                                .map(i -> new CustomWorkHandler())
                                .toArray(CustomWorkHandler[]::new));
        // 启动disruptor线程
        // BatchEventProcessor::run
        disruptor.start();
        // 获取ringbuffer环，用于接取生产者生产的事件
        RingBuffer<MessageModel> ringBuffer = disruptor.getRingBuffer();
        return ringBuffer;
    }

    @Bean
    public RingBuffer<MessageModel> multiConsumerRingBuffer() {
        CustomEventFactory eventFactory = new CustomEventFactory();
        int bufferSize = 16;
        RingBuffer<MessageModel> ringBuffer =
                RingBuffer.createMultiProducer(
                        eventFactory, bufferSize, new BlockingWaitStrategy());
        SequenceBarrier barrier = ringBuffer.newBarrier();
        int consumerCount = 3;
        // 多线程消费者
        WorkerPool<MessageModel> workerPool =
                new WorkerPool<>(
                        ringBuffer,
                        barrier,
                        ExceptionHandlers.defaultHandler(),
                        IntStream.range(0, consumerCount)
                                .boxed()
                                .map(i -> new CustomWorkHandler())
                                .toArray(CustomWorkHandler[]::new));
        // 主要是为了生产者添加时判断是否会覆盖末尾的未消费对象
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
        // WorkProcessor::run
        workerPool.start(Executors.newFixedThreadPool(consumerCount));
        return ringBuffer;
    }

    @Bean
    public RingBuffer<MessageModel> controlMultiConsumerRingBuffer() {
        CustomEventFactory factory = new CustomEventFactory();
        int bufferSize = 16;
        Disruptor<MessageModel> disruptor =
                new Disruptor<>(
                        factory,
                        bufferSize,
                        Executors.defaultThreadFactory(),
                        ProducerType.MULTI,
                        new BlockingWaitStrategy());

        int consumerCount = 3;
        CustomWorkHandler[] customWorkHandlers =
                IntStream.range(0, consumerCount)
                        .boxed()
                        .map(i -> new CustomWorkHandler())
                        .toArray(CustomWorkHandler[]::new);
        EventHandlerGroup<MessageModel> eventHandlerGroup =
                disruptor.handleEventsWithWorkerPool(customWorkHandlers);

        disruptor.start();
        // 可控方式，支持停止
        // disruptor.shutdown();
        return disruptor.getRingBuffer();
    }
}
