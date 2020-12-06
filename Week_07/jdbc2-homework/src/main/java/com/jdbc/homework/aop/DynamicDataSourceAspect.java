package com.jdbc.homework.aop;

import com.jdbc.homework.annotation.Datasource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * @author Redick
 * @date 2020/12/5 11:37 下午
 */
@Configuration
@Aspect
@Slf4j
public class DynamicDataSourceAspect {

    @Pointcut("@annotation(com.jdbc.homework.annotation.Datasource)")
    public void pointCut() {}

    @Before("pointCut()")
    public void before(JoinPoint point)
    {
        Object target = point.getTarget();
        String methodName = point.getSignature().getName();
        Class<?>[] clazz = target.getClass().getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        try {
            Method method = clazz[0].getMethod(methodName, parameterTypes);
            if (method != null && method.isAnnotationPresent(Datasource.class)) {
                Datasource data = method.getAnnotation(Datasource.class);
                DynamicDataSourceContainer.putDataSource(data.value());
            }
        } catch (Exception e) {
            log.error(String.format("Choose DataSource error, method:%s, msg:%s", methodName, e.getMessage()));
        }
    }

    @After("pointCut()")
    public void after(JoinPoint point) {
        DynamicDataSourceContainer.clearDataSource();
    }
}
