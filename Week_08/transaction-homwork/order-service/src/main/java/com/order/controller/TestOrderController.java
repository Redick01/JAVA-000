package com.order.controller;

import api.AccountService;
import api.StockService;
import com.order.controller.dto.OrderPayRequestDTO;
import com.order.controller.dto.OrderRequestDTO;
import com.order.mapper.Order;
import com.order.mapper.OrderMapper;
import com.order.util.SnowFlakeUtil;
import dto.DeleteStockRequestDTO;
import dto.PayAccountRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * @author liupenghui
 * @date 2020/12/11 12:11 上午
 */
@RestController
@Slf4j
public class TestOrderController {

    private final OrderMapper orderMapper;

    private final AccountService accountService;

    private final StockService stockService;

    @Autowired
    public TestOrderController(OrderMapper orderMapper, AccountService accountService, StockService stockService) {
        this.orderMapper = orderMapper;
        this.accountService = accountService;
        this.stockService = stockService;
    }

    @RequestMapping(value = "/order/addOrder")
    @Transactional(rollbackFor = Exception.class)
    public String addOrder(@RequestBody OrderRequestDTO requestDTO) {
        Order order = new Order();
        order.setCreateTime(new Date());
        order.setCount(requestDTO.getProductCount());
        order.setStatus(0);
        order.setNumber(SnowFlakeUtil.generateKey().toString());
        order.setUserId(requestDTO.getUserId());
        order.setProductId(requestDTO.getProductId());
        order.setTotalAmount(requestDTO.getTotalAmount());
        if (orderMapper.insertSelective(order) > 0) {
            DeleteStockRequestDTO stockRequestDTO = new DeleteStockRequestDTO();
            stockRequestDTO.setProductId(requestDTO.getProductId());
            stockRequestDTO.setStockNum(requestDTO.getProductCount());
            if (stockService.deleteStock(stockRequestDTO)) {
                return "SUCCESS";
            } else {
                //todo 简单测试，将异常抛到前端。。。
                throw new RuntimeException("add order filed");
            }
        }
        return "FAILED";
    }

    @RequestMapping(value = "/order/payOrder")
    @Transactional(rollbackFor = Exception.class)
    public String payOrder(@RequestBody OrderPayRequestDTO requestDTO) {
        String orderNo = requestDTO.getOrderNo();
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (Objects.isNull(order)) {
            log.debug("the order is null");
            return "FAILED";
        }
        if (orderMapper.updateByOrderNo(orderNo) > 0) {
            PayAccountRequestDTO request = new PayAccountRequestDTO();
            request.setUserId(requestDTO.getUserId());
            request.setPayAmount(new BigDecimal(order.getTotalAmount()));
            if (accountService.pay(request)) {
                return "SUCCESS";
            } else {
                //todo 简单测试，将异常抛到前端。。。
                throw new RuntimeException("pay order filed");
            }
        }
        return "FAILED";
    }
}
