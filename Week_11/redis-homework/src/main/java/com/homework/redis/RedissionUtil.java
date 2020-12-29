package com.homework.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.IntegerCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author liupenghui
 * @date 2020/12/29 10:49 下午
 */
public class RedissionUtil {

    private final RedissonClient redissonClient;

    public RedissionUtil(String host, String port, String password) {
        Config config = new Config();
        SingleServerConfig singleConfig = config.useSingleServer();
        singleConfig.setAddress(String.format("redis://%s:%s", host, port));
        if (!StringUtils.isEmpty(password)) {
            singleConfig.setPassword(password);
        }
        //数据库编号
        singleConfig.setDatabase(0);
        //连接超时，单位：毫秒
        singleConfig.setConnectTimeout(50000);
        //最小空闲连接数
        singleConfig.setConnectionMinimumIdleSize(10);
        //连接池大小
        singleConfig.setConnectionPoolSize(20);
        //连接空闲超时
        singleConfig.setIdleConnectionTimeout(100);
        //
        singleConfig.setClientName("lock-1");
        redissonClient = Redisson.create(config);
    }

    /**
     * 加锁
     * @param key
     * @return
     */
    public Boolean lock(String key, long waitTime, long leaseTime) {
        boolean b = false;
        try {
            RLock rLock = redissonClient.getLock(key);
            // 说下参数 waitTime：锁的存活时间 leaseTime：锁的延长时间 后面的参数是单位
            b = rLock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 释放锁
     * @param key
     */
    public void unlock(String key){
        try {
            RLock rLock = redissonClient.getLock(key);
            if(null!=rLock){
                rLock.unlock();
                rLock.forceUnlock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计数
     * @param key
     * @param namespace
     * @return
     */
    public long setCounter(String key, String namespace) {
        try {
            RMap<String, Integer> rMap = redissonClient.getMapCache(namespace, IntegerCodec.INSTANCE);
            rMap.putIfAbsent(key, 0);
            return rMap.addAndGet(key, 1);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 负计数
     * @param key
     * @param namespace
     * @return
     */
    public long delCounter(String key, String namespace) {
        try {
            RMap<String, Integer> rMap = redissonClient.getMapCache(namespace, IntegerCodec.INSTANCE);
            rMap.putIfAbsent(key, 0);
            int value = rMap.get(key);
            if (value <= 0) {
                return 0;
            } else {
                return rMap.addAndGet(key, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
