package com.jdbc.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Redick
 * @date 2020/12/5 6:34 下午
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class Application {

    public static void main(String[] args) {
        new SpringApplication(Application.class).run(args);
    }
}
