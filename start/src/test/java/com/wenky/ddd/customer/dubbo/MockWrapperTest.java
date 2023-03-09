package com.wenky.ddd.customer.dubbo;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import com.wenky.commons.dubbo.model.HandleResultEnum;
import com.wenky.ddd.ITest;
import com.wenky.provider.dubbo.service.IHelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-03-07 09:58
 */
@Slf4j
public class MockWrapperTest extends ITest {

    @Autowired private MockWrapper mockWrapper;

    @DubboReference(version = "1.0", group = "custom1")
    private IHelloService iHelloService;

    @Test
    public void bizErrorTest() {
        // IOException会被直接抛出，不属于RpcException MockClusterInvoker#invoke 101,不会返回mock结果
        // 通过重写DecodeableRpcResult#getException方法，同时将异常包装成RpcException，使其能正常进入mock逻辑
        DubboInvokeResult first = mockWrapper.BizError();
        log.info("---MockWrapperTest---, first:{}", first);
        Assert.assertNull(first);

        DubboInvokeResult second = iHelloService.BizError();
        log.info("---MockWrapperTest---, second:{}", second);
        Assert.assertNotNull(second);
    }

    @Test
    public void customMockTest() {
        DubboInvokeResult first = mockWrapper.customMock();
        log.info("---MockWrapperTest---, first:{}", first);
        Assert.assertNotNull(first);
        Assert.assertEquals(first.getCode(), Integer.valueOf(1001));

        // IOException会被直接抛出，由于不属于RpcException所以不会走mock逻辑
        DubboInvokeResult second = mockWrapper.customIOError();
        log.info("---MockWrapperTest---, second:{}", second);
        Assert.assertNull(second);
    }

    @Test
    public void mockTargetTest() {
        DubboInvokeResult result = mockWrapper.mockTarget();
        log.info("---MockWrapperTest---, result:{}", result);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getCode(), Integer.valueOf(4000));
    }

    @Test
    public void mockForceTest() {
        DubboInvokeResult result = mockWrapper.mockForce();
        log.info("---MockWrapperTest---, result:{}", result);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getCode(), Integer.valueOf(4000));
    }

    @Test
    public void mockFailTest() {
        DubboInvokeResult result = mockWrapper.mockFail();
        log.info("---MockWrapperTest---, result:{}", result);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getCode(), Integer.valueOf(3001));
    }

    @Test
    public void mockDefaultTest() {
        // 直接调用 IHelloServiceMock#BizError 返回结果
        DubboInvokeResult first = mockWrapper.mockDefault();
        log.info("---MockWrapperTest---, first:{}", first);
        Assert.assertNotNull(first);

        DubboInvokeResult second = mockWrapper.mockDefault();
        log.info("---MockWrapperTest---, second:{}", second);
        Assert.assertNotNull(second);
        Assert.assertFalse(first == second);
        Assert.assertEquals(first.getCode(), second.getCode());
        Assert.assertEquals(first.getCode(), HandleResultEnum.IO_EXCEPTION.getCode());
    }
}
