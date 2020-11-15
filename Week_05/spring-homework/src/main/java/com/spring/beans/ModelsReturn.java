package com.spring.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author Redick
 * @Date 2020/11/14 5:00 下午
 */
@Data
@AllArgsConstructor
public class ModelsReturn<T> {

    private String resCode;

    private String resMessage;

    private T resData;
}
