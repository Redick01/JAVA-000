package com.jdbc.homework.aop;

import com.jdbc.homework.enums.DynamicDataSourceEnum;


/**
 * @author Redick
 * @date 2020/12/6 9:59 上午
 */
public class DynamicDataSourceContainer {

    private static final ThreadLocal<DynamicDataSourceEnum> holder = new ThreadLocal<DynamicDataSourceEnum>();

    public static void putDataSource(DynamicDataSourceEnum dataSource){
        holder.set(dataSource);
    }

    public static DynamicDataSourceEnum getDataSource(){
        return holder.get();
    }

    public static void clearDataSource() {
        holder.remove();
    }
}
