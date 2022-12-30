package com.wenky.provider.dubbo.service.impl;

import com.wenky.provider.dao.entity.Customer;
import com.wenky.provider.dubbo.service.IHelloService;
import com.wenky.provider.refresh.config.CustomProperties;
import com.wenky.provider.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2022-12-29 14:27
 */
@DubboService(version = "1.0")
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
        return customerService.getByName(name);
    }
}
