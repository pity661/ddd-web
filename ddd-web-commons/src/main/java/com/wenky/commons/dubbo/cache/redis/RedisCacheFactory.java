package com.wenky.commons.dubbo.cache.redis;

import org.apache.dubbo.cache.Cache;
import org.apache.dubbo.cache.support.AbstractCacheFactory;
import org.apache.dubbo.common.URL;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-02-08 16:04
 */
public class RedisCacheFactory extends AbstractCacheFactory {
    @Override
    protected Cache createCache(URL url) {
        return new RedisCache(url);
    }
}
