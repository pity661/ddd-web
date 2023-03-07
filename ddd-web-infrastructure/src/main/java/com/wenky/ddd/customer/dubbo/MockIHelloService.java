package com.wenky.ddd.customer.dubbo;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import java.io.IOException;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-07 10:55
 */
// MockInvoker#getInvoker#getMockObject -> mockClass.newInstance()初始化，每次调用会生成一个新的对象
public class MockIHelloService extends AbstractIHelloService {

    // mock类需要实现目标调用接口
    // mock方法签名需要与调用方法签名一致
    public DubboInvokeResult IOError() throws IOException {
        return DubboInvokeResult.builder().code(2000).message("mock service").build();
    }
}
