package com.wenky.provider.dubbo.service.impl;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import com.wenky.provider.dao.entity.Customer;
import com.wenky.provider.dubbo.service.IHelloService;
import com.wenky.provider.refresh.config.CustomProperties;
import com.wenky.provider.service.CustomerService;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Value;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-29 14:27
 */
@Slf4j
@DubboService(
        // 设置超时时间
        timeout = 2 * 1000,
        // 不发起重试
        retries = -1,
        version = "1.0",
        group = "custom1",
        // 服务器端并发执行(占用线程池线程数)不能超过5个 executes
        // 每个客户端并发执行(占用连接请求数)不能超过2个 actives
        methods = {@Method(name = "getName", executes = 5, actives = 2)})
// @DubboService(version = "1.0")
@RequiredArgsConstructor
public class HelloServiceImpl implements IHelloService {

    private final AtomicInteger atomicInteger = new AtomicInteger(0);
    private final CustomProperties customProperties;
    private final CustomerService customerService;

    @Value("${server.port}")
    private String port;

    @Override
    public String getName() {
        return customProperties.getName();
    }

    @Override
    public Customer getByName(String name) {
        String index = RpcContext.getContext().getAttachment("index"); // 传递隐式参数
        log.info("attachment, index:{}", index);
        Customer customer = customerService.getByName(name);
        customer.setPort(port);
        log.info(customer.toString());
        return customer;
    }

    @Override
    public DubboInvokeResult getWrapperByName(String name) {
        return DubboInvokeResult.success(getByName(name));
    }

    @Override
    public void update(Customer customer) {
        customerService.update(customer);
    }

    @Override
    public DubboInvokeResult IOError() throws IOException {
        throw new IOException("dubbo provider throw IOException");
    }

    @Override
    public DubboInvokeResult RuntimeError() {
        throw new RuntimeException("dubbo provider throw RuntimeException");
    }

    @Override
    public DubboInvokeResult BizError() {
        throw new RuntimeException("dubbo provider throw BizException");
    }

    @Override
    public Integer timeout() throws InterruptedException {
        Integer accessTimes;
        if ((accessTimes = atomicInteger.addAndGet(1)) > 3) {
            return accessTimes;
        }
        TimeUnit.SECONDS.sleep(5L);
        return null;
    }
}
