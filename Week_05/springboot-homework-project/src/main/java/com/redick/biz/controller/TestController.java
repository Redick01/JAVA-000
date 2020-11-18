package com.redick.biz.controller;

import com.redick.annotation.MyCache;
import com.redick.cache.CacheC;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Author Redick
 * @Date 2020/11/18 11:10 下午
 */
@Slf4j
@RestController
public class TestController {

    @RequestMapping("/cache/sayHello")
    @MyCache
    public String sayHello(String str) {
        try {
            if (StringUtils.isBlank(str)) {
                log.info("请求参数为空");
                return "null";
            }
            if (Objects.isNull(CacheC.getCache(str.hashCode()))) {
                log.info("缓存不存在，直接返回");
                return str;
            }
            log.info("从缓存中获取");
            return String.valueOf(CacheC.getCache(str.hashCode()));
        } catch (Exception e) {
            log.error("异常：{[]}", e);
            return "null";
        }
    }
}
