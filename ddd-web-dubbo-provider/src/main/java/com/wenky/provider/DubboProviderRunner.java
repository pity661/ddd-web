package com.wenky.provider;

import com.alibaba.csp.sentinel.adapter.dubbo.config.DubboAdapterGlobalConfig;
import com.wenky.commons.dubbo.model.DubboInvokeResult;
import org.apache.dubbo.rpc.AsyncRpcResult;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-07 18:45
 */
@Component
public class DubboProviderRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        DubboAdapterGlobalConfig.setConsumerFallback(
                (invoker, invocation, ex) ->
                        AsyncRpcResult.newDefaultAsyncResult(
                                DubboInvokeResult.builder()
                                        .code(1000)
                                        .message("sentinel default result")
                                        .build(),
                                invocation));
    }
}
