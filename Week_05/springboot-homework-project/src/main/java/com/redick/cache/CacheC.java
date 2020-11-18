package com.redick.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Author Redick
 * @Date 2020/11/18 11:09 下午
 */
@Slf4j
public class CacheC {

    private final static Cache<Object, Object> CACHE;

    static {
        CACHE = CacheBuilder.newBuilder()
                .maximumSize(100000)
                .concurrencyLevel(8)
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build();
    }

    public static Object getCache(Object key) throws Exception {
        return CACHE.getIfPresent(key);
    }

    public static Object addCache(Object key, Object value) {
        CACHE.put(key, value);
        return value;
    }
}
