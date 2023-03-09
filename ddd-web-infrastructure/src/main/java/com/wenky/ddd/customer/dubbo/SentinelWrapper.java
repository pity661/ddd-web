package com.wenky.ddd.customer.dubbo;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import com.wenky.provider.dubbo.service.IHelloService;
import java.io.IOException;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.stereotype.Component;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-07 14:17
 */
@Component
public class SentinelWrapper extends AbstractIHelloService {

    // sentinel.dubbo.provider.filter=com.alibaba.csp.sentinel.adapter.dubbo.SentinelDubboProviderFilter
    // sentinel.dubbo.consumer.filter=com.alibaba.csp.sentinel.adapter.dubbo.SentinelDubboConsumerFilter
    // dubbo.application.context.name.filter=com.alibaba.csp.sentinel.adapter.dubbo.DubboAppContextFilter

    @DubboReference(
            version = "1.0",
            group = "custom1",
            methods = {@Method(name = "getWrapperByName", cache = "caffeine")})
    private IHelloService iHelloService;

    // 限流配置在provider，配置缓存后不会触发限流规则
    public DubboInvokeResult getWrapperByNameCached(String name) {
        return iHelloService.getWrapperByName(name);
    }

    @DubboReference(version = "1.0", group = "custom1")
    private IHelloService iHelloService1;

    @Override
    public DubboInvokeResult getWrapperByName(String name) {
        return iHelloService1.getWrapperByName(name);
    }

    @Override
    public DubboInvokeResult IOError() throws IOException {
        return iHelloService1.IOError();
    }

    @Override
    public DubboInvokeResult BizError() {
        return iHelloService1.BizError();
    }

    @Override
    public DubboInvokeResult RuntimeError() {
        return iHelloService1.RuntimeError();
    }
}
