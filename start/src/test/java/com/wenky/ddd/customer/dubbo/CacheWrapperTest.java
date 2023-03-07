package com.wenky.ddd.customer.dubbo;

import com.wenky.ddd.ITest;
import com.wenky.provider.dao.entity.Customer;
import com.wenky.provider.dubbo.service.IHelloService;
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
public class CacheWrapperTest extends ITest {

    @Autowired private CacheWrapper cacheWrapper;

    @DubboReference(version = "1.0", group = "custom1")
    private IHelloService iHelloService;

    @Test
    public void test() {
        // 通过同一reference调用，缓存结果对象相同
        Customer customer = cacheWrapper.getByName("wenky");
        Assert.assertNotNull(customer);
        Assert.assertTrue(customer == cacheWrapper.getByName("wenky"));

        // 不同reference调用，返回结果对象不同
        customer = iHelloService.getByName("wenky");
        Assert.assertNotNull(customer);
        Assert.assertFalse(customer == iHelloService.getByName("wenky"));
    }
}
