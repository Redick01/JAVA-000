package cache.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * @author liupenghui
 * @date 2021/1/7 8:54 下午
 */
@Configuration
public class SpringBootRedisConfiguration {

    @Bean
    public RedisConnectionFactory lettuceConnectionFactory() {
        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration().master("mymaster")
                .sentinel("192.168.0.104", 26381);
        return new LettuceConnectionFactory(configuration);
    }
}
