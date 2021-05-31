package com.redick.biz.controller;

import com.redick.biz.mapper.Account;
import com.redick.biz.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liupenghui
 * @date 2021/5/29 10:20 下午
 */
@Slf4j
@RestController
public class TransactionTestController {

    private final TransactionService transactionService;

    private Account account = null;

    @Autowired
    public TransactionTestController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/trans/transTest")
    @Transactional(propagation = Propagation.REQUIRED)
    public String testTransaction() {
        transactionService.insertA();
        try {
            transactionService.testA();
        } catch (Exception e) {
            e.printStackTrace();
        }
        transactionService.insertA();
        return "test";
    }
}
