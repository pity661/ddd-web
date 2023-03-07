package com.wenky.ddd.customer.dubbo;

import com.wenky.provider.dubbo.service.IHelloService;
import org.apache.dubbo.config.annotation.DubboReference;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-06 15:27
 */
// @Component
public class TokenWrapper extends AbstractIHelloService {

    @DubboReference(
            version = "1.0",
            group = "custom1",
            // 指定url，绕开注册中心可以直接调用provider
            url = "dubbo://127.0.0.1:20880?token=eae9102a-4c83-44af-be6e-d78c6137ec26")
    private IHelloService iHelloService;

    // TokenFilter#invoke
    @Override
    public String getName() {
        return iHelloService.getName();
    }
}
