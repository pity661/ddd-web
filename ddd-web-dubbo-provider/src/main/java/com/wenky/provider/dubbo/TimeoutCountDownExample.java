package com.wenky.provider.dubbo;

import java.util.concurrent.TimeUnit;
import org.apache.dubbo.rpc.TimeoutCountDown;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-02-28 11:34
 */
public class TimeoutCountDownExample {

    public static void main(String[] args) throws InterruptedException {
        TimeoutCountDown t = TimeoutCountDown.newCountDown(1, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(2);
        System.out.println(t.timeRemaining(TimeUnit.SECONDS));
    }
}
