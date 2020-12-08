package com.homework.sharding.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Redick
 * @date 2020/12/8 1:36 下午
 */
public class DateUtil {

    public static final String YYYY_MM_DD = "yyyyMMdd";

    /**
     * 获取指定格式日期，时间字符串
     * @param source date
     * @param pattern data pattern
     * @return format YYYYMMdd date
     */
    public static String getDateTimeStr(Date source, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(source);
    }
}
