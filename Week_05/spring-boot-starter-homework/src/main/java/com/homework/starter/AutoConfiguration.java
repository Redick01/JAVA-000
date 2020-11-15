package com.homework.starter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author Redick
 * @Date 2020/11/15 11:51 上午
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties({HomeworkProperties.class, StudentProperties.class})
@Slf4j
public class AutoConfiguration {

    @Autowired
    private HomeworkProperties properties;
    @Autowired
    private StudentProperties studentProperties;

    @Bean
    public HomeworkService homeworkService() {
        HomeworkService service = new HomeworkService(properties);
        String result = service.work(properties.getValue());
        log.info("===>第一个装配的bean：[{}]", result);
        return service;
    }

    @Bean
    public Klass klass() {
        Klass klass = new Klass();
        List<Student> list = convert(studentProperties.getStudent());
        klass.setStudents(list);
        klass.dong();
        return klass;
    }

    @Bean
    public ISchool iSchool() {
        log.info("===>通过School执行Klass的dong()");
        return new School();
    }

    /**
     * 数据转化
     * @param val
     * @return
     */
    public List<Student> convert(String val) {
        if (StringUtils.isBlank(val)) {
            return null;
        }
        return JSONObject.parseArray(val, Student.class);
    }
}
