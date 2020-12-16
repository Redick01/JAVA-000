package com.renminbiaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Redick
 * @date 2020/12/9 11:38 下午
 */
@SpringBootApplication
@ImportResource(value = {"classpath:dubbo.xml"})
public class RenminbiAccountApplication {

    public static void main(String[] args) {
        new SpringApplication(RenminbiAccountApplication.class).run(args);
    }
}
