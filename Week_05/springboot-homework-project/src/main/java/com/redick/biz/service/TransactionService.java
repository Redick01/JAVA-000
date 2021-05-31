package com.redick.biz.service;

import com.redick.biz.mapper.Account;
import com.redick.biz.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author liupenghui
 * @date 2021/5/29 10:22 下午
 */
@Service
public class TransactionService {


    private final AccountMapper accountMapper;

    private Account account = null;

    @Autowired
    public TransactionService(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void required() {
        insertA();
        testA();
    }

    @Transactional(propagation = Propagation.NESTED)
    public void testA() {
        insertB();
        account.getBalance();
        insertB();
    }

    public void insertA() {
        Account account = new Account();
        account.setBalance(new Long(100));
        account.setCreateTime(new Date());
        account.setFreezeAmount(new Long(0));
        account.setUpdateTime(new Date());
        account.setUserId("123");
        accountMapper.insertSelective(account);
    }

    public void insertB() {
        Account account = new Account();
        account.setBalance(new Long(200));
        account.setCreateTime(new Date());
        account.setFreezeAmount(new Long(0));
        account.setUpdateTime(new Date());
        account.setUserId("456");
        accountMapper.insertSelective(account);
    }
}
