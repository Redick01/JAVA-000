package com.homework.sharding.biz.controller;

import com.homework.sharding.biz.entity.TbOrder;
import com.homework.sharding.biz.service.TbOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Redick
 * @date 2020/12/8 1:47 下午
 */
@RestController
@Slf4j
public class TbOrderController {

    private final TbOrderService tbOrderService;

    @Autowired
    public TbOrderController(TbOrderService tbOrderService) {
        this.tbOrderService = tbOrderService;
    }

    @RequestMapping(value = "/shardingTable/addOrder", method = RequestMethod.POST)
    @ResponseBody
    public String addOrder(Integer userId) {
        try {
            if (null == userId) {
                userId = 11;
            }
            tbOrderService.addOrder(userId);
        } catch (Exception e) {
            log.error("==>异常：{[]}", e);
            return "FAILED";
        }
        return "SUCCESS";
    }

    @RequestMapping(value = "/shardingTable/queryOrder", method = RequestMethod.GET)
    @ResponseBody
    public TbOrder queryOrder(Long userId, String orderNo) {
        try {
            return tbOrderService.getOrder(orderNo, userId);
        } catch (Exception e) {
            log.error("==>异常：{[]}", e);
            return null;
        }
    }
}
