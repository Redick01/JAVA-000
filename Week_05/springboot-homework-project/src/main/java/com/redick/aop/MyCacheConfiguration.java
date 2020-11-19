package com.redick.aop;

import com.redick.annotation.MyCache;
import com.redick.cache.CacheC;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author Redick
 * @Date 2020/11/18 11:08 下午
 */
@Configuration
@Aspect
@Slf4j
public class MyCacheConfiguration {

    @Pointcut("@annotation(com.redick.annotation.MyCache)")
    public void cache() {}

    /**
     * 执行环绕增强
     * @param joinPoint
     * @return
     */
    @Around("cache()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object obj = null;
        try {
            obj = joinPoint.proceed();
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature)signature;
            Annotation annotation = methodSignature.getMethod().getAnnotation(MyCache.class);
            if (!Objects.isNull(annotation) && Objects.isNull(CacheC.getCache(obj.hashCode()))) {
                int expireTime = ((MyCache) annotation).expireTime();
                TimeUnit timeUnit = ((MyCache) annotation).timeUnit();
                return CacheC.addCache(obj.hashCode(), obj, expireTime, timeUnit);
            }
            // 缓存obj
            return CacheC.addCache(obj.hashCode(), obj, 100, TimeUnit.SECONDS);
        } catch (Throwable e) {
            log.error("发生异常：{[]}", e);
        }
        return obj;
    }
}
