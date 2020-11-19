package com.redick.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author Redick
 * @Date 2020/11/18 11:06 下午
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
@Inherited
public @interface MyCache {
    /**
     * 缓存时间
     * @return
     */
    int expireTime() default 60;

    /**
     * 时间格式
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
