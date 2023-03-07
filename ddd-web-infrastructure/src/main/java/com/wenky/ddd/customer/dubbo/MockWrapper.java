package com.wenky.ddd.customer.dubbo;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import com.wenky.provider.dubbo.service.IHelloService;
import java.io.IOException;
import org.apache.dubbo.config.annotation.DubboReference;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-03 10:53
 */
// @Component
public class MockWrapper extends AbstractIHelloService {

    @DubboReference(
            // 消费者对该服务的方法调用直接返回null
            // mock = "force:return null",
            // 消费者对该服务的方法调用在失败后返回null，不抛异常
            // mock = "fail:return null",
            // 服务提供者出现异常(宕机或业务异常)，返回null
            mock = "return null", // 同 mock = "return",
            version = "1.0",
            group = "custom1")
    private IHelloService iHelloService;

    // MockClusterInvoker#invoke
    @Override
    public DubboInvokeResult IOError() throws IOException {
        return iHelloService.IOError();
    }

    @DubboReference(
            // 请求失败返回自定义结果
            mock = "return {\"code\": 1000, \"message\": \"请求失败，mock结果\"}",
            version = "1.0",
            group = "custom1")
    private IHelloService iHelloService1;

    // MockInvoker#invoke -> 118#normalizeMock
    public DubboInvokeResult mock() throws IOException {
        return iHelloService1.IOError();
    }

    @DubboReference(
            // mock方法签名必须与调用目标方法一致
            mock = "com.wenky.ddd.customer.dubbo.MockIHelloService",
            version = "1.0",
            group = "custom1")
    private IHelloService iHelloService2;

    public DubboInvokeResult mockTarget() throws IOException {
        return iHelloService2.IOError();
    }

    @DubboReference(
            // mock方法签名必须与调用目标方法一致
            mock = "force:com.wenky.ddd.customer.dubbo.MockIHelloService",
            version = "1.0",
            group = "custom1")
    private IHelloService iHelloService3;

    // MockClusterInvoker#invoke -> 89
    // 直接调用mock方法，不会发起远程调用
    public DubboInvokeResult mockForce() throws IOException {
        return iHelloService3.IOError();
    }

    @DubboReference(
            // 请求失败返回自定义结果
            mock = "fail:return {\"code\": 3000, \"message\": \"请求失败，fail mock结果\"}",
            version = "1.0",
            group = "custom1")
    private IHelloService iHelloService4;

    public DubboInvokeResult mockFail() throws IOException {
        return iHelloService4.IOError();
    }

    @DubboReference(
            // 请求失败返回自定义结果
            mock = "true", // mock = "default",
            version = "1.0",
            group = "custom1",
            cache = "caffeine")
    private IHelloService iHelloService5;

    public DubboInvokeResult mockDefault() throws IOException {
        return iHelloService5.IOError();
    }
}
