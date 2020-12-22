package com.homework.rpc.reigister;

import com.homework.rpc.api.RegisterCenter;
import com.homework.rpc.api.ServiceProviderDesc;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;

import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liupenghui
 * @date 2020/12/18 11:36 下午
 */
@Slf4j
public class ZookeeperRegistry implements RegisterCenter {

    private final CuratorFramework zkClient;


    public ZookeeperRegistry(String hostPort, String nameSpace) {
        try {
            CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                    .connectString(hostPort)
                    .namespace(nameSpace)
                    .retryPolicy(new ExponentialBackoffRetry(1000, 3))
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
    public void register(String serviceName, Integer port) {
        try {
            ServiceProviderDesc serviceProviderDesc = ServiceProviderDesc.builder()
                    .host("192.168.3.78")
                    .port(port)
                    .serviceClass(serviceName)
                    .build();
            if (null == zkClient.checkExists().forPath("/" + serviceName)) {
                zkClient.create()
                        .creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath("/" + serviceName, "service".getBytes());
            }
            zkClient.create()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath("/" + serviceName + "/" + serviceProviderDesc.getHost() + ":" + serviceProviderDesc.getPort(), "provider".getBytes());
        } catch (KeeperException.NodeExistsException e) {
            log.warn("Node" + serviceName + "already exists.", e);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public static void main(String[] args) throws Exception {
        ZookeeperRegistry zookeeperRegistry = new ZookeeperRegistry("192.168.58.45:2181", "Rpcfx");
        zookeeperRegistry.register(ZookeeperRegistry.class.getName(), 8080);
        String s = zookeeperRegistry.getNodeData(ZookeeperRegistry.class.getName());

        System.out.println(s);

        List<String> stringList = zookeeperRegistry.getChildren(ZookeeperRegistry.class.getName());
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
        return zkClient.getChildren().forPath("/" + nameSpace);
    }

    /**
     * get node data
     * @param serviceName serviceName
     * @return node data
     * @throws Exception exception
     */
    public String getNodeData(String serviceName) throws Exception {
        return new String(zkClient.getData().forPath("/" + serviceName));
    }

    public void watchNode(String serviceName, Watcher watcher) throws Exception {
        zkClient.getData().usingWatcher(watcher).forPath(serviceName);
    }

    public void addConnectionStateListener(ConnectionStateListener connectionStateListener) {
        zkClient.getConnectionStateListenable()
                .addListener(connectionStateListener);
    }
}
