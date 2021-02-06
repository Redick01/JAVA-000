package com.redick.biz.controller;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.dromara.soul.client.springmvc.annotation.SoulSpringMvcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liupenghui
 * @date 2021/1/13 11:58 下午
 */
@RestController
@RequestMapping("/test")
@SoulSpringMvcClient(path = "/test/**")
@Slf4j
public class SoulTestController {

    public static AtomicInteger integer = new AtomicInteger(0);

    @PostMapping("/hello")
    public String hello(String req) throws InterruptedException {
        log.info("====>Server 8086");
        /*if ((integer.intValue() % 2) == 0) {
            integer.incrementAndGet();
            throw new NullPointerException();
        }*/
        //Thread.sleep(60000);
        integer.incrementAndGet();
        return req;
    }
}
