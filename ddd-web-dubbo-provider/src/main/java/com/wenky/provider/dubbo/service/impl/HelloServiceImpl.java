package com.wenky.provider.dubbo.service.impl;

import com.wenky.provider.dubbo.service.IHelloService;
import com.wenky.provider.refresh.config.CustomProperties;
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

    @Override
    public String getName() {
        return customProperties.getName();
    }
}
