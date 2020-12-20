package com.homework.rpc;

import com.homework.rpc.api.RegisterCenter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liupenghui
 * @date 2020/12/18 11:36 下午
 */
@Slf4j
public class ZkRegistry implements RegisterCenter {

    private final CuratorFramework zkClient;


    public ZkRegistry(String hostPort, String nameSpace) {
        try {
            CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                    .namespace(nameSpace)
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
            zkClient.create()
                    .creatingParentContainersIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(serviceName);
        } catch (KeeperException.NodeExistsException e) {
            log.warn("Node" + serviceName + "already exists.", e);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static void main(String[] args) throws Exception {
        ZkRegistry zkRegistry = new ZkRegistry("192.168.3.97:2181", "222");
        zkRegistry.register("/order", "111");
        System.out.println("111");
        String s = zkRegistry.getNodeData("/order");

        System.out.println(s);

        List<String> stringList = zkRegistry.getChildren("/");
        for (String s1 : stringList) {
            System.out.println(s1);
        }
    }

    @Override
    public String getImplName(String serviceName) {
        return null;
    }

    /**
     * get Node
     * @param nameSpace namespace
     * @return Node List
     * @throws Exception exception
     */
    @Override
    public List<String> getChildren(String nameSpace) throws Exception {
        return zkClient.getChildren().forPath(nameSpace);
    }

    /**
     * get node data
     * @param serviceName serviceName
     * @return node data
     * @throws Exception exception
     */
    public String getNodeData(String serviceName) throws Exception {
        return new String(zkClient.getData().forPath(serviceName));
    }

    public void watchNode(String serviceName, Watcher watcher) throws Exception {
        zkClient.getData().usingWatcher(watcher).forPath(serviceName);
    }

    public void addConnectionStateListener(ConnectionStateListener connectionStateListener) {
        zkClient.getConnectionStateListenable()
                .addListener(connectionStateListener);
    }
}
