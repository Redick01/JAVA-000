package com.redick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author Redick
 * @Date 2020/11/18 11:11 下午
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class Application {
    public static void main(String[] args) {
        new SpringApplication(Application.class).run(args);
    }
}
