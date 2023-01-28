package com.wenky.provider.framework.disruptor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-01-16 18:20
 */
public class LockSupportExample {

    public static void main(String[] args) throws InterruptedException {
        Thread thread =
                new Thread(
                        () -> {
                            System.out.println(
                                    "start park, thread: " + Thread.currentThread().getName());
                            // 如果线程被interrupt了，就会结束park
                            LockSupport.park();
                            System.out.println("end park");
                            // 会重置当前线程的打断标记
                            System.out.println(Thread.interrupted());
                            // 不会重置当前线程的打断标记
                            System.out.println(Thread.currentThread().isInterrupted());
                        });
        thread.start();

        TimeUnit.SECONDS.sleep(2);
        System.out.println("interrupt, thread: " + Thread.currentThread().getName());
        thread.interrupt();
        TimeUnit.SECONDS.sleep(2);
    }
}
