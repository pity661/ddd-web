package com.wenky.ddd.customer.dubbo;

import com.wenky.commons.dubbo.model.DubboInvokeResult;
import com.wenky.ddd.ITest;
import com.wenky.provider.dao.entity.Customer;
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
 * @create: 2023-03-03 11:41
 */
@Slf4j
public class CacheWrapperTest extends ITest {

    @Autowired private CacheWrapper cacheWrapper;

    @DubboReference(version = "1.0", group = "custom1")
    private IHelloService iHelloService;

    @Test
    public void cachedResultTest() {
        // 缓存结果对象相同
        Customer first = cacheWrapper.getByName("wenky");
        log.info("---CacheWrapperTest---, first:{}", first);
        Assert.assertNotNull(first);
        Customer second = cacheWrapper.getByName("wenky");
        log.info("---CacheWrapperTest---, second:{}", second);
        Assert.assertTrue(first == second);
    }

    @Test
    public void unCachedResultTest() {
        // 非缓存结果对象不同
        Customer first = iHelloService.getByName("wenky");
        log.info("---CacheWrapperTest---, first:{}", first);
        Assert.assertNotNull(first);
        Customer second = iHelloService.getByName("wenky");
        log.info("---CacheWrapperTest---, second:{}", second);
        Assert.assertFalse(first == second);
    }

    @Test
    public void RuntimeErrorTest() {
        // 原始流程会把异常的请求结果也缓存起来。
        // 通过重写DecodeableRpcResult#hasException方法，实现异常结果判断
        DubboInvokeResult first = cacheWrapper.RuntimeError();
        log.info("---CacheWrapperTest---, first:{}", first);
        DubboInvokeResult second = cacheWrapper.RuntimeError();
        log.info("---CacheWrapperTest---, second:{}", second);
        // 异常结果不会缓存
        Assert.assertFalse(first == second);
    }
}
