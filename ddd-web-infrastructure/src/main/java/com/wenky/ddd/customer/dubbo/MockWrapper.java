package com.wenky.ddd.customer.dubbo;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import com.wenky.provider.dubbo.service.IHelloService;
import java.io.IOException;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcException;

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
    public DubboInvokeResult BizError() {
        return iHelloService.BizError();
    }

    @DubboReference(
            // 请求失败返回自定义结果
            mock = "return {\"code\": 1001, \"message\": \"请求失败，mock结果\"}",
            version = "1.0",
            group = "custom1")
    private IHelloService iHelloService1;

    // MockInvoker#invoke -> 118#normalizeMock
    public DubboInvokeResult customMock() {
        return iHelloService1.BizError();
    }

    public DubboInvokeResult customIOError() {
        try {
            return iHelloService1.IOError();
        } catch (IOException | RpcException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DubboReference(
            // mock方法签名必须与调用目标方法一致
            mock = "com.wenky.ddd.customer.dubbo.MockIHelloService",
            version = "1.0",
            group = "custom1")
    private IHelloService iHelloService2;

    public DubboInvokeResult mockTarget() {
        return iHelloService2.BizError();
    }

    @DubboReference(
            // mock方法签名必须与调用目标方法一致
            mock = "force:com.wenky.ddd.customer.dubbo.MockIHelloService",
            version = "1.0",
            group = "custom1")
    private IHelloService iHelloService3;

    // MockClusterInvoker#invoke -> 89
    // 直接调用mock方法，不会发起远程调用
    public DubboInvokeResult mockForce() {
        return iHelloService3.BizError();
    }

    @DubboReference(
            // 请求失败返回自定义结果
            mock = "fail:return {\"code\": 3001, \"message\": \"请求失败，fail mock结果\"}",
            version = "1.0",
            group = "custom1")
    private IHelloService iHelloService4;

    public DubboInvokeResult mockFail() {
        return iHelloService4.BizError();
    }

    @DubboReference(
            // 请求失败返回自定义结果
            mock = "true", // mock = "default",
            version = "1.0",
            group = "custom1",
            cache = "caffeine")
    private IHelloService iHelloService5;

    public DubboInvokeResult mockDefault() {
        return iHelloService5.BizError();
    }
}
