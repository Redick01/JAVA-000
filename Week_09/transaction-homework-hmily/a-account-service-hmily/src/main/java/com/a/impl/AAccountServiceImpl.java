package com.a.impl;

import api.AccountService;
import com.a.mapper.AccountMapper;
import dto.PayAccountRequestDTO;
import enums.ExchangeTypeEnum;
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
public class AAccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

    @Autowired
    public AAccountServiceImpl(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean exchange(PayAccountRequestDTO requestDTO) {
        String userId = requestDTO.getUserId();
        int payAmount = requestDTO.getExchangeAmount();
        return accountMapper.exchangeToDollar(userId, payAmount, payAmount) > 0;
    }


    @Transactional(rollbackFor = Exception.class)
    public void confirm(PayAccountRequestDTO requestDTO) {
        log.info("===================>confirm开始执行");
        int payAmount = requestDTO.getExchangeAmount();
        int freezeAmount = requestDTO.getExchangeAmount();
        ExchangeTypeEnum typeEnum = requestDTO.getExchangeType();
        if (ExchangeTypeEnum.REMINBI_TO_DOLLAR.equals(typeEnum)) {
            payAmount = payAmount * 7;
            accountMapper.confirm(requestDTO.getUserId(), payAmount, freezeAmount);
        }
        log.info("===================>confirm执行结束");
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancel(PayAccountRequestDTO requestDTO) {
        log.info("===================>cancel开始执行");
        accountMapper.cancel(requestDTO.getUserId(), requestDTO.getExchangeAmount());
        log.info("===================>cancel执行结束");
    }
}
