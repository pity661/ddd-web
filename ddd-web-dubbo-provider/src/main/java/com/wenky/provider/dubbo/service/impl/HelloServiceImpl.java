package com.wenky.provider.dubbo.service.impl;

import com.wenky.provider.dao.entity.Customer;
import com.wenky.provider.dubbo.service.IHelloService;
import com.wenky.provider.refresh.config.CustomProperties;
import com.wenky.provider.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-29 14:27
 */
@DubboService(version = "1.0", group = "custom1")
// @DubboService(version = "1.0")
@RequiredArgsConstructor
public class HelloServiceImpl implements IHelloService {

    private final CustomProperties customProperties;
    private final CustomerService customerService;

    @Override
    public String getName() {
        return customProperties.getName();
    }

    @Override
    public Customer getByName(String name) {
        String index = RpcContext.getContext().getAttachment("index"); // 传递隐式参数
        return customerService.getByName(name);
    }

    @Override
    public void update(Customer customer) {
        customerService.update(customer);
    }
}
