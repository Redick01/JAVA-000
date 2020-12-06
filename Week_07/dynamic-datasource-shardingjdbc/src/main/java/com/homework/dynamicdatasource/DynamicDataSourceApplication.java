package com.homework.dynamicdatasource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Redick
 * @date 2020/12/6 10:48 上午
 */
@SpringBootApplication
@MapperScan("com.homework.dynamicdatasource.biz.mapper")
public class DynamicDataSourceApplication {

    public static void main(String[] args) {
        new SpringApplication(DynamicDataSourceApplication.class).run(args);
    }
}
