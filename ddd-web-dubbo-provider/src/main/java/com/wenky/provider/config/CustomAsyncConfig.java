package com.wenky.provider.config;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author 克林
 * @date 2023/7/13 14:30
 */
@Slf4j
@Configuration
@ConditionalOnBean(annotation = EnableAsync.class)
// 通过EnableConfigurationProperties注入的bean不受condition条件限制
@EnableConfigurationProperties(AsyncConfigProperties.class)
@RequiredArgsConstructor
public class CustomAsyncConfig implements AsyncConfigurer {

    public static ThreadLocal<Integer> VALUE = new ThreadLocal<>();
    private final AsyncConfigProperties asyncConfigProperties;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncConfigProperties.getCorePoolSize());
        // setAllowCoreThreadTimeOut 和 setKeepAliveSeconds 同时设置才会结束 corePoolSize
        executor.setAllowCoreThreadTimeOut(Boolean.TRUE);
        executor.setKeepAliveSeconds(60);
        executor.setMaxPoolSize(asyncConfigProperties.getMaxPoolSize());
        executor.setQueueCapacity(asyncConfigProperties.getQueueCapacity());
        executor.setThreadNamePrefix(asyncConfigProperties.getThreadNamePrefix());
        // 装饰者模式获取ThreadLocal属性
        executor.setTaskDecorator(getTaskDecorator());
        executor.setRejectedExecutionHandler(getRejectedExecutionHandler());
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

    public static RejectedExecutionHandler getRejectedExecutionHandler() {
        // 线程拒绝策略
        //        new ThreadPoolExecutor.AbortPolicy(); // 抛异常
        //        new ThreadPoolExecutor.CallerRunsPolicy(); // 在当前线程执行任务，阻塞式
        //        new ThreadPoolExecutor.DiscardOldestPolicy(); // 抛弃最先提交的任务
        //        new ThreadPoolExecutor.DiscardPolicy(); // 直接抛弃当前任务

        // 自定义线程拒绝策略
        RejectedExecutionHandler rejectedExecutionHandler =
                (task, executor) -> {
                    // 打印任务日志
                    log.info("任务被抛弃");
                };
        return rejectedExecutionHandler;
    }

    private TaskDecorator getTaskDecorator() {
        return runnable -> {
            // 调用异步执行线程
            Integer value = VALUE.get();
            return () -> {
                try {
                    // 当前执行任务线程
                    VALUE.set(value);
                    runnable.run();
                } finally {
                    VALUE.remove();
                }
            };
        };
    }
}
