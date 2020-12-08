package com.homework.sharding.biz.service;

import com.homework.sharding.biz.entity.TbOrder;
import com.homework.sharding.biz.mapper.TbOrderMapper;
import com.homework.sharding.util.DateUtil;
import com.homework.sharding.util.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * order service
 * @author Redick
 * @date 2020/12/7 11:53 下午
 */
@Service
public class TbOrderService {

    private TbOrderMapper tbOrderMapper;

    @Autowired
    public TbOrderService(TbOrderMapper tbOrderMapper) {
        this.tbOrderMapper = tbOrderMapper;
    }

    /**
     * add order
     * @param userId user_id
     */
    public void addOrder(Long userId) {
        TbOrder order = new TbOrder();
        order.setBusinessDate(DateUtil.getDateTimeStr(new Date(), DateUtil.YYYY_MM_DD));
        order.setUserId(userId);
        order.setOrderNo(String.valueOf(SnowFlakeUtil.generateKey().longValue()));
        tbOrderMapper.insertSelective(order);
    }
}
