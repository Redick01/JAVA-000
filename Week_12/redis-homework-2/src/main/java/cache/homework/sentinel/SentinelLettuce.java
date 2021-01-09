package cache.homework.sentinel;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.Utf8StringCodec;
import io.lettuce.core.masterslave.MasterSlave;
import io.lettuce.core.masterslave.StatefulRedisMasterSlaveConnection;

/**
 * @author liupenghui
 * @date 2021/1/5 10:42 上午
 */
public final class SentinelLettuce {



    public static RedisCommands<String, String> getRedisCommands() {
        RedisURI redisUri = RedisURI.builder()
                .withSentinel("192.168.0.104", 26381)
                .withSentinelMasterId("ebab6b28d2bd521b0ac8a933cee6920d69ddeaab")
                .build();
        RedisClient redisClient = RedisClient.create();
        StatefulRedisMasterSlaveConnection<String, String> connection = MasterSlave.connect(redisClient, new Utf8StringCodec(), redisUri);
        // 只允许从从节点读取数据
        connection.setReadFrom(ReadFrom.SLAVE);
        RedisCommands<String, String> command = connection.sync();
        return command;
    }

    public static void main(String[] args) {
        RedisCommands<String, String> command = getRedisCommands();
        SetArgs setArgs = SetArgs.Builder.nx().ex(5);
        command.set("kkk", "llll", setArgs);
        String value = command.get("kkk");
    }
}
