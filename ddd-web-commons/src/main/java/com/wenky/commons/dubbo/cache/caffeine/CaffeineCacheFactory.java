package com.wenky.commons.dubbo.cache.caffeine;

import org.apache.dubbo.cache.Cache;
import org.apache.dubbo.cache.support.AbstractCacheFactory;
import org.apache.dubbo.common.URL;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-02-28 16:04
 */
public class CaffeineCacheFactory extends AbstractCacheFactory {

    @Override
    // CacheFilter#invoke
    // AbstractCacheFactory#getCache 每个方法生成一个cache
    protected Cache createCache(URL url) {
        return new CaffeineCache(url);
    }
}
