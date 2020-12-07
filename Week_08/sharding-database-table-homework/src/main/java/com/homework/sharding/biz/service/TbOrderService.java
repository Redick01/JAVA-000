package com.homework.sharding.biz.service;

import com.homework.sharding.biz.mapper.TbOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
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
}
