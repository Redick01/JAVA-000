package com.homework.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author Redick
 * @Date 2020/11/15 7:23 下午
 */
@ConfigurationProperties(prefix = "student")
@Data
public class StudentProperties {

    private String student;
}
