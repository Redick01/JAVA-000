package com.homework.dynamicdatasource.biz.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface TbOrderMapper extends Mapper<TbOrder> {

    TbOrder selectByOrderNo(@Param("orderNo") String orderNo);
}