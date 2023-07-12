package com.wenky.provider.framework.canal.configuration;

import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author 克林
 * @date 2023/7/12 14:29
 */
@Slf4j
public class CanalAsyncConfig {

    @Bean(name = "canalAsync")
    public Executor getCanalAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setAllowCoreThreadTimeOut(Boolean.FALSE);
        executor.setMaxPoolSize(1);
        executor.setTaskDecorator(getTaskDecorator());
        executor.initialize();
        return executor;
    }

    private TaskDecorator getTaskDecorator() {
        return runnable -> {
            System.out.println("canal监听start");
            return () -> runnable.run();
        };
    }
}
