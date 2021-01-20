package com.account.impl;

import api.AccountService;
import com.account.mapper.AccountMapper;
import dto.PayAccountRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.soul.client.dubbo.common.annotation.SoulDubboClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Redick
 * @date 2020/12/9 10:57 下午
 */
@Service("accountService")
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

    @Autowired
    public AccountServiceImpl(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    @SoulDubboClient(path = "/pay", desc = "支付")
    public boolean pay(PayAccountRequestDTO requestDTO) {
        return accountMapper.update(requestDTO.getUserId(), requestDTO.getPayAmount()) > 0;
    }


    @Transactional(rollbackFor = Exception.class)
    public void confirm(PayAccountRequestDTO requestDTO) {
        log.info("===================>confirm开始执行");
        accountMapper.confirm(requestDTO.getUserId(), requestDTO.getPayAmount());
        log.info("===================>confirm执行结束");
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancel(PayAccountRequestDTO requestDTO) {
        log.info("===================>cancel开始执行");
        accountMapper.cancel(requestDTO.getUserId(), requestDTO.getPayAmount());
        log.info("===================>cancel执行结束");
    }
}
