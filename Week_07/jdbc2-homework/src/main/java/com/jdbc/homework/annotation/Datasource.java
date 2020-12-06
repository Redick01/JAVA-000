package com.jdbc.homework.annotation;

import com.jdbc.homework.enums.DynamicDataSourceEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Redick
 * @date 2020/12/5 11:34 下午
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Datasource {

    DynamicDataSourceEnum value() default DynamicDataSourceEnum.WRITE;
}
