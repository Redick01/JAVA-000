package com.redick.annotation;

import java.lang.annotation.*;

/**
 * @Author Redick
 * @Date 2020/11/18 11:06 下午
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
@Inherited
public @interface MyCache {
}
