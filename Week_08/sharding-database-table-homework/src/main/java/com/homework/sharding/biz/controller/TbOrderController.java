package com.homework.sharding.biz.controller;

import com.homework.sharding.biz.service.TbOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Redick
 * @date 2020/12/8 1:47 下午
 */
@RestController
@Slf4j
public class TbOrderController {

    private TbOrderService tbOrderService;

    @Autowired
    public TbOrderController(TbOrderService tbOrderService) {
        this.tbOrderService = tbOrderService;
    }

    @RequestMapping(value = "/shardingTable/addOrder", method = RequestMethod.POST)
    public String addOrder(@RequestBody Long userId) {
        try {
            tbOrderService.addOrder(userId);
        } catch (Exception e) {
            log.error("==>异常：{[]}", e);
            return "FAILED";
        }
        return "SUCCESS";
    }
}
