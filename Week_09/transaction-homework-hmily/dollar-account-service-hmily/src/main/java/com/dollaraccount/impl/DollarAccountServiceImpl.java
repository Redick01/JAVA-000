package com.dollaraccount.impl;

import api.AccountService;
import dto.PayAccountRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Redick
 * @date 2020/12/9 10:57 下午
 */
@Service("accountService")
@Slf4j
public class DollarAccountServiceImpl implements AccountService {


    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean exchange(PayAccountRequestDTO requestDTO) {

        return true;
    }


    @Transactional(rollbackFor = Exception.class)
    public void confirm(PayAccountRequestDTO requestDTO) {
        log.info("===================>confirm开始执行");
        log.info("===================>confirm执行结束");
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancel(PayAccountRequestDTO requestDTO) {
        log.info("===================>cancel开始执行");
        log.info("===================>cancel执行结束");
    }
}
