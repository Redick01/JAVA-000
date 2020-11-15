package com.homework.starter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author Redick
 * @Date 2020/11/15 8:00 下午
 */
@Slf4j
public class School implements ISchool {

    @Autowired
    private Klass klass;

    @Override
    public void ding() {
        klass.dong();
    }
}
