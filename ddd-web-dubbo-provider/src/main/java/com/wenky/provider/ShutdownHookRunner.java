package com.wenky.provider;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-30 10:02
 */
@Component
public class ShutdownHookRunner implements CommandLineRunner {
    /**
     * 优雅停机时，JVM关闭调用的钩子
     * kill -15 PID
     *
     * 当执行`curl http://127.0.0.1:8081/sleep`,有线程被挂起也会执行当前hook
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        Runtime.getRuntime()
                .addShutdownHook(
                        new Thread("ddd-web-shutdownHook") {
                            @Override
                            public void run() {
                                System.out.println("application is shutdown!");
                            }
                        });
    }
}
