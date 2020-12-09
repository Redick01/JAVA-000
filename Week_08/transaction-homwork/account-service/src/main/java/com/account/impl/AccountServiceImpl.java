package com.account.impl;

import api.AccountService;
import dto.PayAccountRequestDTO;
import org.springframework.stereotype.Service;

/**
 * @author Redick
 * @date 2020/12/9 10:57 下午
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Override
    public boolean pay(PayAccountRequestDTO requestDTO) {
        return false;
    }
}
