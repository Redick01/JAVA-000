package com.homework.starter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author Redick
 * @Date 2020/11/15 6:27 下午
 */
@Data
@Slf4j
public class Klass {
    List<Student> students;

    public void dong(){
        log.info("===>执行Klass的dong()：[{}]", this.getStudents());
    }
}
