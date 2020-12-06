package com.homework.dynamicdatasource.controller;

import com.homework.dynamicdatasource.biz.mapper.TbOrder;
import com.homework.dynamicdatasource.biz.mapper.TbOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Redick
 * @date 2020/12/6 4:07 下午
 */
@RestController
public class OrderController {

    private TbOrderMapper tbOrderMapper;

    @Autowired
    public OrderController(TbOrderMapper tbOrderMapper) {
        this.tbOrderMapper = tbOrderMapper;
    }

    @RequestMapping(value = "/order/getOrderAll", method = RequestMethod.GET)
    @ResponseBody
    public List<TbOrder> getOrderAll() {
        List<TbOrder> order = tbOrderMapper.selectAll();
        return Objects.isNull(order) ? null : order;
    }

    @RequestMapping(value = "/order/getOrderByOrderNo", method = RequestMethod.GET)
    @ResponseBody
    public TbOrder getOrderByOrderNo(String orderNo) {
        TbOrder order = tbOrderMapper.selectByOrderNo(orderNo);
        return Objects.isNull(order) ? null : order;
    }

    @RequestMapping(value = "/order/addOrder", method = RequestMethod.POST)
    @ResponseBody
    public TbOrder addOrder() {
        TbOrder order = new TbOrder();
        order.setBusinessDate("20201206");
        order.setUserId((long)1);
        order.setOrderNo(UUID.randomUUID().toString());
        tbOrderMapper.insertSelective(order);
        return order;
    }
}
