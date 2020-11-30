## 作业

#### 必做1 按自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率。

##### 方法1 存储过程，循环一条一条插入
````

DROP PROCEDURE IF EXISTS insert_order_1;

CREATE PROCEDURE insert_order_1(num int)
    BEGIN
        DECLARE i int DEFAULT 0;
        START TRANSACTION;
        WHILE i < num
            DO
            insert into tb_order (order_no, user_id, refund_status, business_date, trans_amount, payment_amount, next_process_count, sharding_id)
            value (ROUND(20201129235959592950, i) + i, i, '00', '20201129', i, i, 0, i % 10);
            set  i = i + 1;
            end while;
        COMMIT;
    end;

call insert_order_1(1000000);
耗时：1 m 13 s 141 ms
````


## 学习笔记

### 第十四课

#### 从单机到集群
随着数据量的增大，读写并发的增加，系统可用性要求的提升，单机MySQL面临: 

1、容量有限，难以扩容

2、读写压力，QPS过大，特别是分析类需求会影响到业务事务
 
3、可用性不足，宕机问题


#### MySql主从分离
主库写binlog，从库拉relay log。

binlog格式：ROW、Statement、Mixed

```
1、异步复制：主库执行sql记录binlog执行完commit后binlog记录完毕，从库读到binlog到本地的relay log然后提交到sql执行器，执行binlog最后提交
异步赋值：网络或机器故障，会造成数据不一致

2、半同步复制：需要启用插件，至少一个从库获取到binlog后会给主库ACK，确保数据一致

3、组复制（MGR）：基于分布式Paxos协议实现，保证数据一致性
```
#### 主从复制的局限性
```
1、主从延迟
2、应用测需要配合读写分离
3、不解决高可用问题
```

#### MySql读写分离
```
1、硬编码：配置多个数据源，例如：配置master和slave数据源
2、使用基于AbstractRoutingDataSource和自定义注解方式简化自动切换数据源
3、使用ShardingSphere-jdbc
以上三种对业务都还有入侵并且对旧系统不友好
4、使用MyCat/ShardingSphere-Proxy的Master-Slave，需要部署并配置中间件，模拟一个mysql服务器，对业务无入侵
```

#### MySql高可用
```
1、读写分离，提升读的处理能力 
2、故障转移，提供 failover 能力
加上业务侧连接池的心跳重试，实现断线重连，业务不间断，降低 RTO 和 RPO

1.高可用0主从手动切换
如果主节点挂掉，将某个从改成主; 重新配置其他从节点。 修改应用数据源配置。

问题：
（1）可能数据不一致。
（2）需要人工干预。
（3）代码和配置的侵入性。

2.高可用1主从手动切换
用LVS+Keepalived实现多个节点的探活+请求路由。
配置VIP或DNS实现配置不变更。

问题：
（1）手工处理主从切换
（2）大量的配置和脚本定义

3.高可用2 MHA
MHA(Master High Availability)目前在 MySQL 高可用方面是一个相对成熟的解决 方案，它由日本 DeNA 公司的 youshimaton(现就职于 Facebook 公司)开发，是一 套优秀的作为 MySQL 高可用性环境下故障切换和主从提升的高可用软件。
基于Perl语言开发，一般能在30s内实现主从切换。 切换时，直接通过SSH复制主节点的日志。

问题：
（1）需要配置SSH
（2）至少三台

4.高可用3 MGR
主节点挂了，将自动选择某个从改成主，无需人工干预，基于组复制，保证数据一致性

问题：
（1）外部获得状态变更需要读取数据库
（2）外部需要使用LVS/VIP配置


MGR特点：
（1）高一致性
（2）高容错性
（3）高扩展性
（4）高灵活性

5.高可用4 MySql Cluster

6.高可用5 Orchestrator
```

