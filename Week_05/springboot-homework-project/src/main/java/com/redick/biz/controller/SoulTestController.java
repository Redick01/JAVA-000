package com.redick.biz.controller;

import lombok.extern.slf4j.Slf4j;
import org.dromara.soul.client.springmvc.annotation.SoulSpringMvcClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liupenghui
 * @date 2021/1/13 11:58 下午
 */
@RestController
@RequestMapping("/test")
@SoulSpringMvcClient(path = "/test/**")
@Slf4j
public class SoulTestController {

    @PostMapping("/hello")
    public String hello(String req) {
        log.info("====>Server 8086");
        return req;
    }
}
