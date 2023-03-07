package com.wenky.ddd.customer.dubbo;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import com.wenky.ddd.ITest;
import java.io.IOException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
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

    @Test
    public void test() throws IOException {
        DubboInvokeResult result = mockWrapper.IOError();
        Assert.assertNull(result);
    }

    @Test
    public void mockTest() throws IOException {
        DubboInvokeResult result = mockWrapper.mock();
        log.info(result.toString());
        Assert.assertEquals(
                Optional.ofNullable(result).map(DubboInvokeResult::getCode).orElse(null),
                Integer.valueOf(1000));
    }

    @Test
    public void mockTargetTest() throws IOException {
        DubboInvokeResult result = mockWrapper.mockTarget();
        log.info(result.toString());
        Assert.assertEquals(
                Optional.ofNullable(result).map(DubboInvokeResult::getCode).orElse(null),
                Integer.valueOf(2000));
    }

    @Test
    public void mockForceTest() throws IOException {
        DubboInvokeResult result = mockWrapper.mockForce();
        log.info(result.toString());
        Assert.assertEquals(
                Optional.ofNullable(result).map(DubboInvokeResult::getCode).orElse(null),
                Integer.valueOf(2000));
    }

    @Test
    public void mockFailTest() throws IOException {
        DubboInvokeResult result = mockWrapper.mockFail();
        log.info(result.toString());
        Assert.assertEquals(
                Optional.ofNullable(result).map(DubboInvokeResult::getCode).orElse(null),
                Integer.valueOf(3000));
    }

    @Test
    public void mockDefaultTest() throws IOException {
        DubboInvokeResult result = mockWrapper.mockDefault();
        log.info(result.toString());
        // 在执行CacheFilter#invoke 106直接抛出异常了，不会缓存异常结果，所以后续调用不会取缓存值
        Assert.assertFalse(result == mockWrapper.mockDefault());
        Assert.assertEquals(
                Optional.ofNullable(result).map(DubboInvokeResult::getCode).orElse(null),
                Integer.valueOf(4000));
    }
}
