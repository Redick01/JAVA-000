package com.stock.impl;

import api.StockService;
import com.stock.mapper.StockMapper;
import dto.DeleteStockRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Redick
 * @date 2020/12/10 12:17 上午
 */
@Service("stockService")
@Slf4j
public class StockServiceImpl implements StockService {

    private final StockMapper stockMapper;

    @Autowired
    public StockServiceImpl(StockMapper stockMapper) {
        this.stockMapper = stockMapper;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean deleteStock(DeleteStockRequestDTO requestDTO) {

        return stockMapper.deleteStock(requestDTO.getProductId(), requestDTO.getStockNum()) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public void confirm(DeleteStockRequestDTO requestDTO) {
        log.info("===================>confirm开始执行");
        stockMapper.confirm(requestDTO.getProductId(), requestDTO.getStockNum());
        log.info("===================>confirm执行完毕");
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancel(DeleteStockRequestDTO requestDTO) {
        log.info("===================>cancel开始执行");
        stockMapper.cancel(requestDTO.getProductId(), requestDTO.getStockNum());
        log.info("===================>cancel执行完毕");
    }
}
