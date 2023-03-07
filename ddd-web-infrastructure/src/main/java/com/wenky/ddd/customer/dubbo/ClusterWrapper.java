package com.wenky.ddd.customer.dubbo;

import com.wenky.provider.dubbo.service.IHelloService;
import org.apache.dubbo.config.annotation.DubboReference;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-03 10:56
 */
// @Component
public class ClusterWrapper extends AbstractIHelloService {

    // 默认配置 cluster="failover", retries=2
    // failover 失败自动切换服务提供者重试 FailoverClusterInvoker
    @DubboReference(version = "1.0", group = "custom1", cluster = "failover", retries = 2)
    private IHelloService iHelloService;

    @Override
    public String getName() {
        return iHelloService.getName();
    }

    public IHelloService getService() {
        return iHelloService;
    }
}
