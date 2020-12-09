package com.stock.impl;

import api.StockService;
import dto.DeleteStockRequestDTO;
import org.springframework.stereotype.Service;

/**
 * @author Redick
 * @date 2020/12/10 12:17 上午
 */
@Service("stockService")
public class StockServiceImpl implements StockService {

    @Override
    public boolean deleteStock(DeleteStockRequestDTO requestDTO) {
        return false;
    }
}
