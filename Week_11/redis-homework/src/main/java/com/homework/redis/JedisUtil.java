package com.homework.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Collections;
import java.util.Set;

/**
 * @author liupenghui
 * @date 2020/12/28 9:50 下午
 */
public class JedisUtil {

    /**
     * 设置锁成功
     */
    private static final String LOCK_SUCCESS = "OK";
    /**
     * 原子设置参数，如果不存在设置锁
     */
    private static final String SET_IF_NOT_EXIT = "NX";
    /**
     * 带有过期时间参数 EX-单位是秒
     */
    private static final String SET_WITH_EXPIRE_TIME = "EX";
    /**
     * 释放锁成功
     */
    private static final Long RELEASE_SUCCESS = 1L;
    /**
     * redis分布式锁使用的db 1-db1
     */
    private static final int DB_INDEX = 1;

    /**
     * 生成分布式锁key 所用字符串 业务类型
     */
    private static final String BUSINESS = "ACC";
    /**
     * 生成分布式锁key 所用字符串 字段名
     */
    private static final String FIELD_NAME = "CARD_NUM";

    private JedisPool jedisPool;

    public JedisUtil(String host, int port) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(30);
        jedisPool = new JedisPool(config, host, port, 30000, "Ruyixing2017");
    }

    /**
     * 获取分布式锁
     * @param lockKey 锁-key
     * @param requestId 锁-value（用于释放锁）
     * @param expireTime 锁过期时间
     * @return 获取锁结果
     */
    public boolean tryLock(String lockKey, String requestId, int expireTime) {
        try {
            Jedis jedis = jedisPool.getResource();
            jedis.select(DB_INDEX);
            String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIT, SET_WITH_EXPIRE_TIME, expireTime);
            return LOCK_SUCCESS.equals(result);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 释放分布式锁
     * @param lockKey 锁-key
     * @param requestId 锁-value（用于释放锁）
     * @return 释放锁结果
     */
    public boolean releaseLock(String lockKey, String requestId) {
        try {
            Jedis jedis = jedisPool.getResource();
            // Lua脚本（2.6.12版本以后redis客户端支持Lua语言）
            // 首先获取锁对应的value值，检查是否与requestId相等，如果相等则删除锁(解锁)
            jedis.select(DB_INDEX);
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end;";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
            return RELEASE_SUCCESS.equals(result);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 计数器
     * @param key 计数器key
     * @param expireTime 超时时间，等于0不超时
     * @return 计数
     */
    public long setCounter(String key, int expireTime) {
        long result = 0;
        try {
            Jedis jedis = jedisPool.getResource();
            result = jedis.incr(key);
            if (expireTime != 0) {
                jedis.expire(key, expireTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public long delCounter(String key) {
        long result = 0;
        try {
            Jedis jedis = jedisPool.getResource();
            result = Long.parseLong(jedis.get(key));
            if (result <= 0) {
                return 0;
            } else {
                result = jedis.decr(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void del(String key) {
        Jedis jedis = jedisPool.getResource();
        jedis.select(2);
        Set<String> sets = jedis.keys(key);
        sets.forEach(e -> jedis.del(e));
    }

    /**
     * 生成分布式锁key 所用的唯一值
     * @param keyValue 分布式锁key 所用的唯一值
     * @return 分布式锁key
     */
    private String getLockKey(String keyValue) {
        return BUSINESS + ":" + keyValue + ":" + FIELD_NAME;
    }


    public static void main(String[] args) {
        JedisUtil jedisUtil = new JedisUtil("r-2ze2bde2c40f39b4.redis.rds.aliyuncs.com", 6379);
        jedisUtil.del("MATCH*");
        System.exit(0);
    }
}
