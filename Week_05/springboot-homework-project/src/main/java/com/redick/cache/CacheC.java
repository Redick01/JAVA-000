package com.redick.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author Redick
 * @Date 2020/11/18 11:09 下午
 */
@Slf4j
public class CacheC {

    private static Cache<Object, Object> CACHE;




    public static Object getCache(Object key) throws Exception {
        if (Objects.isNull(CACHE)) {
            return null;
        }
        return CACHE.getIfPresent(key);
    }

    public static Object addCache(Object key, Object value, int expire, TimeUnit timeUnit) {
        if (Objects.isNull(CACHE)) {
            CACHE = CacheBuilder.newBuilder()
                    .maximumSize(100000)
                    .concurrencyLevel(8)
                    .expireAfterWrite(expire, timeUnit)
                    .build();
        }
        CACHE.put(key, value);
        return value;
    }

}
