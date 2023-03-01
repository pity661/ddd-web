package com.wenky.commons.dubbo.cache.redis;

import org.apache.dubbo.cache.Cache;
import org.apache.dubbo.common.URL;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-02-08 16:05
 */
public class RedisCache implements Cache {
    // TODO 接入springbootdataredis实现缓存功能

    public RedisCache(URL url) {

    }

    @Override
    public void put(Object key, Object value) {}

    @Override
    public Object get(Object key) {
        return null;
    }
}
