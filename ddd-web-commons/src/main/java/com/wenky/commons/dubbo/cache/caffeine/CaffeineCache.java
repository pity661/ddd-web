package com.wenky.commons.dubbo.cache.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.apache.dubbo.cache.Cache;
import org.apache.dubbo.common.URL;

/**
 * @program: ddd-web
 * @description:
 * @author: wenky
 * @create: 2023-02-28 16:04
 */
public class CaffeineCache implements Cache {

    private final com.github.benmanes.caffeine.cache.Cache<Object, Object> cache;

    public CaffeineCache(URL url) {
        final int secondsToLive = url.getParameter("cache.seconds", 180);
        // ExpiringCache while循环中休眠间隔
        // final int intervalSeconds = url.getParameter("cache.interval", 4);
        final int max = url.getParameter("cache.size", 1000);
        cache =
                Caffeine.newBuilder()
                        // 内存溢出前GC会释放缓存内存，释放后SoftReference对象还在，get方法返回null
                        .softValues()
                        // 最大缓存数
                        .maximumSize(max)
                        // 写入后失效时间（若一直未被访问）
                        .expireAfterWrite(secondsToLive, TimeUnit.SECONDS)
                        // 访问后间隔失效时间（被访问后达到指定时间失效）
                        // .expireAfterAccess(intervalSeconds, TimeUnit.SECONDS)
                        .build();
    }

    @Override
    public void put(Object key, Object value) {
        cache.put(key, value);
    }

    @Override
    // CacheFilter#invoke
    public Object get(Object key) {
        Object result = cache.getIfPresent(key);
        return result;
    }
}
