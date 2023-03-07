package com.wenky.provider.dubbo.service;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import com.wenky.provider.dao.entity.Customer;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-29 14:01
 */
public interface IHelloService {

    String getName();

    Customer getByName(String name);

    DubboInvokeResult getWrapperByName(String name);

    void update(Customer customer);

    DubboInvokeResult IOError() throws IOException;

    DubboInvokeResult RuntimeError() throws IOException;

    Integer timeout() throws InterruptedException;

    default CompletableFuture<Customer> getByNameAsync(String name) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return getByName(name);
                });
    }
}
