package cache.homework.sentinel;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.connection.balancer.RandomLoadBalancer;

/**
 * @author liupenghui
 * @date 2021/1/5 10:42 上午
 */
public final class SentinelRedisson {

    private static final String URL = "192.168.0.104:26379,192.168.0.104:26380,192.168.0.104:26381";

    private static final String MASTER_NAME = "mymaster";

    private static final Integer TIMEOUT = 3000;

    private static final Integer IDLE_TIMEOUT = 10000;



    private static RedissonClient config() {
        Config config = new Config();
        config.useSentinelServers()
                .setDatabase(1)
                .setConnectTimeout(TIMEOUT)
                .setIdleConnectionTimeout(IDLE_TIMEOUT)
                .setLoadBalancer(new RandomLoadBalancer())
                .setMasterName(MASTER_NAME)
                .setReadMode(ReadMode.SLAVE)
                .setPingTimeout(1000)
                .setTimeout(3000)
                .setMasterConnectionMinimumIdleSize(32)
                .setMasterConnectionPoolSize(64)
                .setSlaveConnectionMinimumIdleSize(32)
                .setSlaveConnectionPoolSize(64)
                .setRetryAttempts(3)
                .setRetryInterval(1500)
                .setSubscriptionConnectionMinimumIdleSize(1)
                .setSubscriptionConnectionPoolSize(50)
                //可以用"rediss://"来启用SSL连接
                .addSentinelAddress(URL);
        return Redisson.create(config);
    }

    public static RedissonClient getRedisson() {
        return config();
    }

    public static void main(String[] args) {
        RedissonClient client = getRedisson();
        client.getMapCache("kkk");
    }
}
