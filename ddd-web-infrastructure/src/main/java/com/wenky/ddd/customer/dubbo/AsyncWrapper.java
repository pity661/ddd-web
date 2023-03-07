package com.wenky.ddd.customer.dubbo;

import com.wenky.provider.dao.entity.Customer;
import com.wenky.provider.dubbo.service.IHelloService;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-03 10:57
 */
@Slf4j
// @Component(value = "asyncWrapper")
public class AsyncWrapper extends AbstractIHelloService {

    // DubboReference初始化逻辑ReferenceConfig#get
    // 初始化method注解源码，异步调用
    // ReferenceBeanBuilder#configureMethodConfig
    // onreturn不会生效
    @DubboReference(
            version = "1.0",
            group = "custom1",
            methods = {
                @Method(name = "getByName", async = true, onreturn = "asyncWrapper.onreturn")
            })
    private IHelloService iHelloService;

    // 没用，注入时没有初始化onreturnMethod所以不会执行
    // 初始化 AbstractConfig#convertMethodConfig2AsyncInfo
    // 执行 FutureFilter#fireReturnCallback
    public void onreturn(Customer customer, String name) {
        log.info(String.format("name: %s, customer: %s", name, customer.toString()));
    }

    @Override
    // 通过DubboReference::method注解指定方法异步执行实现异步调用
    public Customer getByName(String name) {
        return iHelloService.getByName(name);
    }

    private Customer getByAsyncFuture(String name) {
        CompletableFuture<Customer> future = iHelloService.getByNameAsync(name);
        future =
                future.whenComplete(
                        (c, t) -> {
                            if (t != null) {
                                log.error("execute error", t);
                                return;
                            }
                            log.info(String.format("customer: %s", c.toString()));
                        });
        return future.join();
    }
}
