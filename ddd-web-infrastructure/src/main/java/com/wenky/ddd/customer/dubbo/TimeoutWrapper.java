package com.wenky.ddd.customer.dubbo;

import com.wenky.provider.dubbo.service.IHelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-03 10:56
 */
// @Component
public class TimeoutWrapper extends AbstractIHelloService {

    @DubboReference(
            version = "1.0",
            group = "custom1",
            methods = {@Method(name = "timeout", timeout = 4 * 1000, retries = 4)})
    private IHelloService iHelloService;

    // 线程池任务拒绝策略 AbortPolicyWithReport
    // AsyncToSyncInvoker#invoke -> 61 AsyncRpcResult#get(long timeout, TimeUnit unit)
    // DubboInvoker#doInvoke => timeout
    // HeaderExchangeChannel#request 124
    @Override
    public Integer timeout() throws InterruptedException {
        return iHelloService.timeout();
    }
}
