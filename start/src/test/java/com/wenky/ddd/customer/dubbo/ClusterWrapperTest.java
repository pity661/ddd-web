package com.wenky.ddd.customer.dubbo;

import com.wenky.ddd.ITest;
import com.wenky.provider.dubbo.service.IHelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-03 11:55
 */
public class ClusterWrapperTest extends ITest {

    @Autowired private ClusterWrapper clusterWrapper;

    @DubboReference(version = "1.0", group = "custom1")
    private IHelloService iHelloService;

    @Test
    public void test() {}
}
