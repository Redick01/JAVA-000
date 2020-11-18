package com.redick.aop;

import com.redick.cache.CacheC;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

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
            // 缓存obj
            return Objects.isNull(CacheC.getCache(obj.hashCode())) ? CacheC.addCache(obj.hashCode(), obj) : obj;
        } catch (Throwable e) {
            log.error("发生异常：{[]}", e);
        }
        return obj;
    }
}
