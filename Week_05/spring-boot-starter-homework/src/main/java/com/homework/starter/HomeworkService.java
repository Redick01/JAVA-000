package com.homework.starter;

/**
 * @Author Redick
 * @Date 2020/11/15 5:55 下午
 */
public class HomeworkService {

    HomeworkProperties homeworkProperties;

    public HomeworkService(HomeworkProperties homeworkProperties) {
        this.homeworkProperties = homeworkProperties;
    }

    public String work(String name ) {
        return homeworkProperties.getValue()+ "-" + name;
    }
}
