package com.homework.rpc;

import com.homework.rpc.api.RegisterCenter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.TimeUnit;

/**
 * @author liupenghui
 * @date 2020/12/18 11:36 下午
 */
@Slf4j
public class ZkRegistry implements RegisterCenter {

    private final CuratorFramework zkClient;

    private ZNodeWatcher zNodeWatcher = new ZNodeWatcher();

    public ZkRegistry(String hostPort) {
        try {
            CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                    .connectString(hostPort)
                    .retryPolicy(new RetryNTimes(1, 1000))
                    .connectionTimeoutMs(5000)
                    .sessionTimeoutMs(60000);
            zkClient = builder.build();
            zkClient.getConnectionStateListenable();
            zkClient.start();
            boolean connected = zkClient.blockUntilConnected(10000, TimeUnit.MILLISECONDS);
            if (!connected) {
                throw new IllegalStateException("zookeeper not connected");
            }
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public void register(String serviceName, String implName) {
        try {
            zkClient.create().forPath(serviceName);
        } catch (KeeperException.NodeExistsException e) {
            log.warn("Node" + serviceName + "already exists.", e);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        ZkRegistry zkRegistry = new ZkRegistry("192.168.58.45:2181");
        zkRegistry.register("/order", "111");
        System.out.printf("111");
    }

    @Override
    public String getImplName(String serviceName) {
        return null;
    }

    public class ZNodeWatcher implements Watcher{
        @Override
        public void process(WatchedEvent event) {
            Event.EventType eventType = event.getType();
            Event.KeeperState keeperState =  event.getState();
            String path = event.getPath();
            switch(event.getType()) {
                case None:
                    //connection Error：会自动重连
                    log.info("[Watcher],Connecting...");
                    if(keeperState == Event.KeeperState.SyncConnected){
                        log.info("[Watcher],Connected...");
                        //检测临时节点是否失效等。
                    }
                    break;
                case NodeCreated:
                    log.info("[Watcher],NodeCreated:" + path);
                    break;
                case NodeDeleted:
                    log.info("[Watcher],NodeDeleted:" + path);
                    break;
                default:
                    //
            }
        }
    }
}
