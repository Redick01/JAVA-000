package com.redick.job;

import com.redick.biz.controller.SoulTestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author liupenghui
 * @date 2021/1/30 11:03 下午
 */
//@Component
public class Job {

    //@Autowired
    private ScheduledExecutorService scheduledExecutorService;

    //@PostConstruct
    public void task() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("====>" + SoulTestController.integer.intValue());
        }, 1, 2, TimeUnit.SECONDS);
    }
}
