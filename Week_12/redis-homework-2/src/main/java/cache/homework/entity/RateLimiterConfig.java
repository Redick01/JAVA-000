package cache.homework.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.Duration;

/**
 * @author liupenghui
 * @date 2021/3/6 6:48 下午
 */
@Data
@EqualsAndHashCode
public class RateLimiterConfig implements Serializable {
    private Integer database = 1;

    private String master;

    private String mode = "standalone";

    /**
     * If it is cluster or sentinel mode, separated with `;`.
     */
    private String url;

    /**
     * the password.
     */
    private String password;

    /**
     * Maximum number of "idle" connections in the pool. Use a negative value to
     * indicate an unlimited number of idle connections.
     */
    private int maxIdle = 8;

    /**
     * Target for the minimum number of idle connections to maintain in the pool. This
     * setting only has an effect if it is positive.
     */
    private int minIdle;

    /**
     * Maximum number of connections that can be allocated by the pool at a given
     * time. Use a negative value for no limit.
     */
    private int maxActive = 8;

    /**
     * Maximum amount of time a connection allocation should block before throwing an
     * exception when the pool is exhausted. Use a negative value to block
     * indefinitely.
     */
    private Duration maxWait = Duration.ofMillis(-1);
}
