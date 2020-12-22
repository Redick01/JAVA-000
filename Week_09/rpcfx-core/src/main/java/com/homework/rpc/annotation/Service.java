package com.homework.rpc.annotation;

import java.lang.annotation.*;

/**
 * @author liupenghui
 * @date 2020/12/22 11:50 下午
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Inherited
public @interface Service {
}
