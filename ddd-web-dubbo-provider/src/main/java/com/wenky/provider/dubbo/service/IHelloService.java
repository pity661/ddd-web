package com.wenky.provider.dubbo.service;

import com.wenky.provider.dao.entity.Customer;
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

    void update(Customer customer);

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
