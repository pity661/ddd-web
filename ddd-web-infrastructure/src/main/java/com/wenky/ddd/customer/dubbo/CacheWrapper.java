package com.wenky.ddd.customer.dubbo;

import com.wenky.provider.dao.entity.Customer;
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
public class CacheWrapper extends AbstractIHelloService {

    @DubboReference(
            version = "1.0",
            group = "custom1",
            methods = {
                // 针对单个接口设置缓存
                @Method(name = "getByName", cache = "caffeine")
            })
    private IHelloService iHelloService;

    // CacheFilter#hasException返回false就缓存结果，所以自定义异常处理之后需要重写这段逻辑
    @Override
    // DubboReference::Method给指定方法设置缓存，缓存结果为ValueWrapper包装类，所以结果为null也会缓存
    public Customer getByName(String name) {
        return iHelloService.getByName(name);
    }
}
