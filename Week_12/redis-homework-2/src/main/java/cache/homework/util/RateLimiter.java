package cache.homework.util;

import cache.homework.entity.RateLimiterConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liupenghui
 * @date 2021/3/2 8:47 下午
 */
@Slf4j
public class RateLimiter {

    private static final Long RELEASE_SUCCESS = 1L;


    public static void main(String[] args) throws Exception {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(30);
        config.setMaxIdle(30);
        JedisPool pool = new JedisPool(config, "r-2zebca7d4628bac4.redis.rds.aliyuncs.com", 6379, 2000, "Qrbrqj88");
        Jedis jedis = pool.getResource();
        jedis.select(1);
        BufferedReader file = new BufferedReader(new FileReader("/Users/penghuiliu/geek_learn/JAVA-000/Week_12/redis-homework-2/src/main/resources/scripts/test.lua"));
        String str;
        String str1 = "";
        while ((str = file.readLine()) != null) {
            //System.out.println(str);
            str1 += str;
        }
        /*RateLimiterConfig rateLimiterConfig = new RateLimiterConfig();
        rateLimiterConfig.setUrl("r-2zebca7d4628bac4.redis.rds.aliyuncs.com:6379");
        rateLimiterConfig.setPassword("Qrbrqj88");
        LettuceConnectionFactory lettuceConnectionFactory = createLettuceConnectionFactory(rateLimiterConfig);
        lettuceConnectionFactory.afterPropertiesSet();
        RedisSerializer<String> serializer = new StringRedisSerializer();
        RedisSerializationContext<String, String> serializationContext =
                RedisSerializationContext.<String, String>newSerializationContext().key(serializer).value(serializer).hashKey(serializer).hashValue(serializer).build();
        ReactiveRedisTemplate<String, String> reactiveRedisTemplate = new ReactiveRedisTemplate<>(lettuceConnectionFactory, serializationContext);

        DefaultRedisScript redisScript = new DefaultRedisScript<>();
        String scriptPath = "/scripts/" + "test.lua";
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(scriptPath)));
        redisScript.setResultType(List.class);*/

        List<String> scriptArgs = Arrays.asList(1 + "", 1 + "", Instant.now().getEpochSecond() + "", "2");
        List<String> keys = Arrays.asList("liu", "liu_last");
        Object object = jedis.eval("-- current key\n" +
                "local leaky_bucket_key = KEYS[1]\n" +
                "-- last update key\n" +
                "local last_bucket_key = KEYS[2]\n" +
                "-- capacity\n" +
                "local capacity = tonumber(ARGV[2])\n" +
                "-- the rate of leak water\n" +
                "local rate = tonumber(ARGV[1])\n" +
                "-- request count\n" +
                "local requested = tonumber(ARGV[4])\n" +
                "-- current timestamp\n" +
                "local now = tonumber(ARGV[3])\n" +
                "-- the key life time\n" +
                "local key_lifetime = math.ceil((capacity / rate) + 1)\n" +
                "\n" +
                "\n" +
                "-- the yield of water in the bucket default 0\n" +
                "local key_bucket_count = tonumber(redis.call(\"GET\", leaky_bucket_key)) or 0\n" +
                "\n" +
                "-- the last update time default now\n" +
                "local last_time = tonumber(redis.call(\"GET\", last_bucket_key)) or now\n" +
                "\n" +
                "-- the time difference\n" +
                "local millis_since_last_leak = now - last_time\n" +
                "\n" +
                "-- the yield of water had lasted\n" +
                "local leaks = millis_since_last_leak * rate\n" +
                "\n" +
                "if leaks > 0 then\n" +
                "    -- clean up the bucket\n" +
                "    if leaks >= key_bucket_count then\n" +
                "        key_bucket_count = 0\n" +
                "    else\n" +
                "        -- compute the yield of water in the bucket\n" +
                "        key_bucket_count = key_bucket_count - leaks\n" +
                "    end\n" +
                "    last_time = now\n" +
                "end\n" +
                "\n" +
                "-- is allowed pass default not allow\n" +
                "local is_allow = 0\n" +
                "\n" +
                "local new_bucket_count = key_bucket_count + requested\n" +
                "-- allow\n" +
                "if new_bucket_count <= capacity then\n" +
                "    is_allow = 1\n" +
                "else\n" +
                "    -- not allow\n" +
                "    return {is_allow, new_bucket_count}\n" +
                "end\n" +
                "\n" +
                "-- update the key bucket water yield\n" +
                "redis.call(\"SETEX\", leaky_bucket_key, key_lifetime, new_bucket_count)\n" +
                "\n" +
                "-- update last update time\n" +
                "redis.call(\"SETEX\", last_bucket_key, key_lifetime, now)\n" +
                "\n" +
                "-- return\n" +
                "return {is_allow, new_bucket_count}", keys, scriptArgs);
        //Flux<List<Long>> resultFlux = reactiveRedisTemplate.execute(redisScript, keys, scriptArgs);
        List<Long> result = new ArrayList<>();
        if (object instanceof ArrayList<?>) {
            for (Object o : (List<?>) object) {
                result.add(Long.class.cast(o));
            }
        }
        /*resultFlux.onErrorResume(throwable -> Flux.just(Arrays.asList(1L, -1L)))
                .reduce(new ArrayList<Long>(), (longs, l) -> {
                    longs.addAll(l);
                    return longs;
                }).map(results -> {
            boolean allowed = results.get(0) == 1L;
            Long tokensLeft = results.get(1);
            System.out.println(allowed);
            return allowed;
        }).doOnError(throwable -> System.out.println(throwable.getMessage()));*/

        //Thread.sleep(1000);
        if (result.get(0).equals(RELEASE_SUCCESS)) {
            System.out.println("允许通过");
        } else {
            System.out.println("限流");
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

    private static LettuceConnectionFactory createLettuceConnectionFactory(final RateLimiterConfig rateLimiterConfig) {
        LettuceClientConfiguration lettuceClientConfiguration = getLettuceClientConfiguration(rateLimiterConfig);

        return new LettuceConnectionFactory(redisStandaloneConfiguration(rateLimiterConfig), lettuceClientConfiguration);
    }
    private static LettuceClientConfiguration getLettuceClientConfiguration(final RateLimiterConfig rateLimiterConfig) {
        return LettucePoolingClientConfiguration.builder().poolConfig(getPoolConfig(rateLimiterConfig)).build();
    }
    protected static final RedisStandaloneConfiguration redisStandaloneConfiguration(final RateLimiterConfig rateLimiterConfig) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        String[] parts = StringUtils.split(rateLimiterConfig.getUrl(), ":");
        assert parts != null;
        config.setHostName(parts[0]);
        config.setPort(Integer.parseInt(parts[1]));
        if (rateLimiterConfig.getPassword() != null) {
            config.setPassword(RedisPassword.of(rateLimiterConfig.getPassword()));
        }
        config.setDatabase(rateLimiterConfig.getDatabase());
        return config;
    }
    private static GenericObjectPoolConfig<?> getPoolConfig(final RateLimiterConfig rateLimiterConfig) {
        GenericObjectPoolConfig<?> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(rateLimiterConfig.getMaxActive());
        config.setMaxIdle(rateLimiterConfig.getMaxIdle());
        config.setMinIdle(rateLimiterConfig.getMinIdle());
        if (rateLimiterConfig.getMaxWait() != null) {
            config.setMaxWaitMillis(rateLimiterConfig.getMaxWait().toMillis());
        }
        return config;
    }
}