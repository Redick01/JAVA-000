package com.redick.job;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author liupenghui
 * @date 2021/1/30 11:00 下午
 */
//@Configuration
public class JobConfiguration {

    //@Bean
    public ScheduledExecutorService getJob() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        return executorService;
    }
}
