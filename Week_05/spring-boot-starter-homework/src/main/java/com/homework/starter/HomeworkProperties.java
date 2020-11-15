package com.homework.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author Redick
 * @Date 2020/11/15 12:08 上午
 */
@ConfigurationProperties(prefix = "homework.pro")
@Data
public class HomeworkProperties {

    private String value;
}
