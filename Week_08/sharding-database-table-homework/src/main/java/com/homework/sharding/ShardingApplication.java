package com.homework.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Redick
 * @date 2020/12/7 11:53 下午
 */
@SpringBootApplication
public class ShardingApplication {

    public static void main(String[] args) {
        new SpringApplication(ShardingApplication.class).run(args);
    }
}
