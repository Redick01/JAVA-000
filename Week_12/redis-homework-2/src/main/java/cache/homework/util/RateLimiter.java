package cache.homework.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 * @author liupenghui
 * @date 2021/3/2 8:47 下午
 */
public class RateLimiter {

    public static void main(String[] args) throws Exception {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(30);
        config.setMaxIdle(30);
        JedisPool pool = new JedisPool(config, "r-2zebca7d4628bac4.redis.rds.aliyuncs.com", 6379, 2000, "Qrbrqj88");
        Jedis jedis = pool.getResource();
        jedis.select(1);
        BufferedReader file = new BufferedReader(new FileReader("/Users/penghuiliu/geek_learn/JAVA-000/Week_12/redis-homework-2/src/main/resources/scripts/leaky_bucket_rate_limiter.lua"));
        String str;
        String str1 = "";
        while ((str = file.readLine()) != null) {
            System.out.println(str);
            str1 += str;
        }
        List<String> scriptArgs = Arrays.asList(1 + "", 10 + "", Instant.now().getEpochSecond() + "", "1");
        List<String> keys = Arrays.asList("liu", "hui");
        while (true) {
            jedis.eval(str1, keys, scriptArgs);
        }
    }

    // 容量20
    private final long capacity;
    // 漏水速率 个/秒
    private final int rate;
    // 桶里的水量
    private int used;
    // 上次漏水的时间戳
    private long lastLeakyTimestamp;

    public RateLimiter(long capacity, int rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.used = 0;
        this.lastLeakyTimestamp = System.currentTimeMillis();
    }

    /**
     * 尝试请求
     * @return
     */
    public synchronized boolean tryDo() {
        // 漏水
        leak();
        // 如果桶里的水加上新的请求大于容量，限流
        if ((used + 1) > capacity) {
            return false;
        }
        // 更新桶里的水
        used = used + 1;
        return true;
    }

    /**
     * 漏水
     */
    private void leak() {
        long currentTimestamp = System.currentTimeMillis();
        if (currentTimestamp > lastLeakyTimestamp) {
            long millisSinceLastLeak = currentTimestamp - lastLeakyTimestamp;
            // 漏了多少水
            long leaks = millisSinceLastLeak / 1000 * rate;
            if (leaks > 0) {
                // 如果漏的水大于等于桶里已经加进去的水，将桶里的水清空
                if (used <= leaks) {
                    used = 0;
                } else {
                    // 漏的水小于桶里的水，计算桶里剩余的水
                    used -= leaks;
                }
                this.lastLeakyTimestamp = currentTimestamp;
            }
        }
    }

}