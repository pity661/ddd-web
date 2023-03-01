package com.wenky.ddd.customer;

import com.wenky.ddd.converter.ConsumerConverter;
import com.wenky.ddd.domain.customer.CustomerDO;
import com.wenky.ddd.domain.customer.gateway.CustomerGateway;
import com.wenky.provider.dao.entity.Customer;
import com.wenky.provider.dubbo.service.IHelloService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component(value = "customerGatewayImpl")
@RequiredArgsConstructor
public class CustomerGatewayImpl implements CustomerGateway {
    //    @Autowired private CustomerMapper customerMapper;

    // 初始化method注解源码，异步调用
    // ReferenceBeanBuilder#configureMethodConfig
    // onreturn不会生效
    @DubboReference(
            // 请求结果缓存
            // cache = "caffeine",
            // cache = "redis",
            // 容错策略
            cluster = "",
            timeout = 2 * 1000, // 指定timeout覆盖配置文件全局consumer配置
            version = "1.0",
            group = "custom1",
            methods = {
                // @Method(name = "getByName", async = true, onreturn =
                // "customerGatewayImpl.onreturn")
                // 针对单个接口设置缓存
                @Method(name = "getByName", cache = "caffeine")
            })
    private IHelloService iHelloService;

    private final ConsumerConverter consumerConverter;

    @Override
    public String getName() {
        return iHelloService.getName();
    }

    // 没用，注入时没有初始化onreturnMethod所以不会执行
    // 初始化 AbstractConfig#convertMethodConfig2AsyncInfo
    // 执行 FutureFilter#fireReturnCallback
    public void onreturn(com.wenky.provider.dao.entity.Customer customer, String name) {
        log.info(String.format("name: %s, customer: %s", name, customer.toString()));
    }

    @Override
    public CustomerDO getByName(String name) {
        // 传递隐式参数
        //        RpcContext.getContext().setAttachment("index", "1");

        // 异步调用 同步返回结果
        //        com.wenky.provider.dao.entity.Customer customer = getByMethodAnnotation(name);

        // 异步调用 异步返回结果，但是会因为响应超时报错，通过修改DubboReference::timeout属性解决超时
        //                        com.wenky.provider.dao.entity.Customer customer =
        // getByAsyncFuture(name);

        // 同步调用 同步返回结果
        com.wenky.provider.dao.entity.Customer customer = iHelloService.getByName(name);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // start
        // DubboReference::Method给指定方法设置缓存，缓存结果为ValueWrapper包装类，所以结果为null也会缓存
        customer.setAge(15);
        iHelloService.update(customer);
        com.wenky.provider.dao.entity.Customer customer1 = iHelloService.getByName(name);
        com.wenky.provider.dao.entity.Customer customer2 = iHelloService.getByName(name);
        // end

        return consumerConverter.toDO(customer);
    }

    private Customer getByAsyncFuture(String name) {
        CompletableFuture<com.wenky.provider.dao.entity.Customer> future =
                iHelloService.getByNameAsync(name);

        future =
                future.whenComplete(
                        (c, t) -> {
                            if (t != null) {
                                log.error("", t);
                                return;
                            }
                            log.info(String.format("customer: %s", c.toString()));
                        });

        return future.join();
    }

    // 通过DubboReference::method注解指定方法异步执行实现异步调用
    private Customer getByMethodAnnotation(String name) {
        com.wenky.provider.dao.entity.Customer customer = iHelloService.getByName(name);
        // 不能多次异步调用，RpcContext只会保留最后一次调用的future
        // 调用后可以立刻取出当前future对象
        CompletableFuture<com.wenky.provider.dao.entity.Customer> future =
                RpcContext.getContext().getCompletableFuture();

        com.wenky.provider.dao.entity.Customer customer1 = iHelloService.getByName(name + "1");
        CompletableFuture<com.wenky.provider.dao.entity.Customer> future1 =
                RpcContext.getContext().getCompletableFuture();

        // 异步调用不会直接返回结果，需要在RpcContext中延迟获取
        if (customer == null) {
            log.info(String.format("name: %s, customer not exists!", name));
            //            throw new RuntimeException(String.format("name: %s, customer not exists!",
            // name));
        }

        customer = future.join();
        customer1 = future1.join();

        if (customer1 == null) {
            log.info(String.format("name: %s, customer not exists!", name + "1"));
        }

        return customer;
    }
}
