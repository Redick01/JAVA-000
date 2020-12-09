package com.homework.sharding.biz.mapper;

import com.homework.sharding.biz.entity.TbOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TbOrderMapper {

    @Select("select * from tb_order where user_id=#{userId} and order_no=#{orderNo};")
    TbOrder selectByUserIdAndOrderNo(@Param("userId") long userId, @Param("orderNo") String orderNo);

    @Insert("insert into tb_order (order_no,user_id,business_date) values (#{orderNo},#{userId},#{businessDate});")
    void insertOrder(TbOrder tbOrder);
}