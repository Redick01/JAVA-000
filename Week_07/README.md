## 作业

```
2、(必做)读写分离-动态切换数据源版本1.0 
3、(必做)读写分离-数据库框架版本2.0
这两个作业未能按时完成，本周末补齐
```

#### 必做1 按自己设计的表结构，插入100万订单模拟数据，测试不同方式的插入效率。
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
#### 必做2 mac环境通过docker配置mysql主从
1. 安装docker，docker-compose, 一般安装了docker都会安装docker-compose，可以使用`docker-compose -verison`查看是否安装
2. docker pull mysql:5.7, 拉取mysql5.7镜像
3. 编写docker-compose文件
```
version: '2'
networks:
  byfn:
services:
  master:
    image: 'mysql:5.7'
    restart: always
    container_name: mysql-master
    environment:
      MYSQL_USER: test
      MYSQL_PASSWORD: root
      MYSQL_ROOT_PASSWORD: root
    ports:
      - '3316:3306'
    volumes:
      - "./master/my.cnf:/etc/mysql/my.cnf"
    networks:
      - byfn
  slave:
    image: 'mysql:5.7'
    restart: always
    container_name: mysql-slave
    environment:
      MYSQL_USER: test
      MYSQL_PASSWORD: root
      MYSQL_ROOT_PASSWORD: root
    ports:
      - '3326:3306'
    volumes:
      - "./slave/my.cnf:/etc/mysql/my.cnf"
    networks:
      - byfn
```
4. 在docker-compose.yaml当前目录下，建立两个文件夹，master和slave，里面分别写入文件my.cnf
```
master/my.cnf
[mysqld]
server-id=1
max_allowed_packet=200M
wait_timeout=1814400
log-bin=/var/lib/mysql/mysql-bin

slave/my.cnf
[mysqld]
server-id=2
wait_timeout=1814400
max_allowed_packet = 200M
```
5. 然后在当前docker-compose.yaml 文件目录下执行
```
docker-compose -f docker-compse.yaml up -d
```
6. 启动之后进入master容器
```
docker exec -it mysql-master /bin/bash
mysql -uroot -proot
进入 mysql 终端之后
mysql> create user 'repl'@'%' identified by 'root';
mysql> GRANT REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'repl'@'%'; 
mysql> flush privileges;
mysql> show master status;
+------------------+----------+--------------+------------------+-------------------+
| File             | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+------------------+----------+--------------+------------------+-------------------+
| mysql-bin.000010 |      154 |              |                  |                   |
+------------------+----------+--------------+------------------+-------------------+
1 row in set (0.00 sec)
```
7. 另起一个终端进入slave容器
```
docker exec -it mysql-slave /bin/bash
mysql -uroot -proot
进入 mysql 终端之后
mysql> CHANGE MASTER TO MASTER_HOST='mysql-master', MASTER_PORT=3306,  MASTER_USER='repl', MASTER_PASSWORD='root', MASTER_LOG_FILE='mysql-bin.000010', MASTER_LOG_POS=154;
mysql> start slave;

show slave status\G
*************************** 1. row ***************************
               Slave_IO_State: Waiting for master to send event
                  Master_Host: mysql-master
                  Master_User: repl
                  Master_Port: 3306
                Connect_Retry: 60
              Master_Log_File: mysql-bin.000004
          Read_Master_Log_Pos: 154
               Relay_Log_File: 295f6f6a4e34-relay-bin.000005
                Relay_Log_Pos: 367
        Relay_Master_Log_File: mysql-bin.000004
             Slave_IO_Running: Yes
            Slave_SQL_Running: Yes
              Replicate_Do_DB: 
          Replicate_Ignore_DB: 
           Replicate_Do_Table: 
       Replicate_Ignore_Table: 
      Replicate_Wild_Do_Table: 
  Replicate_Wild_Ignore_Table: 
                   Last_Errno: 0
                   Last_Error: 
                 Skip_Counter: 0
          Exec_Master_Log_Pos: 154
              Relay_Log_Space: 747
              Until_Condition: None
               Until_Log_File: 
                Until_Log_Pos: 0
           Master_SSL_Allowed: No
           Master_SSL_CA_File: 
           Master_SSL_CA_Path: 
              Master_SSL_Cert: 
            Master_SSL_Cipher: 
               Master_SSL_Key: 
        Seconds_Behind_Master: 0
Master_SSL_Verify_Server_Cert: No
                Last_IO_Errno: 0
                Last_IO_Error: 
               Last_SQL_Errno: 0
               Last_SQL_Error: 
  Replicate_Ignore_Server_Ids: 
             Master_Server_Id: 1
                  Master_UUID: 4d83248d-34a8-11eb-beb9-0242ac120003
             Master_Info_File: /var/lib/mysql/master.info
                    SQL_Delay: 0
          SQL_Remaining_Delay: NULL
      Slave_SQL_Running_State: Slave has read all relay log; waiting for more updates
           Master_Retry_Count: 86400
                  Master_Bind: 
      Last_IO_Error_Timestamp: 
     Last_SQL_Error_Timestamp: 
               Master_SSL_Crl: 
           Master_SSL_Crlpath: 
           Retrieved_Gtid_Set: 
            Executed_Gtid_Set: 
                Auto_Position: 0
         Replicate_Rewrite_DB: 
                 Channel_Name: 
           Master_TLS_Version: 
1 row in set (0.01 sec)

Slave_IO_Running: Yes
Slave_SQL_Running: Yes
同步成功
```
8. datagrip连接两个终端，在master上创建数据库、表，添加数据，查看slave
```
master
mysql> select * from user;
+----+------+------+------+
| id | name | sex  | age  |
+----+------+------+------+
|  1 | hhh  | 1    |   11 |
|  2 | qqq  | 2    |   22 |
|  3 | ssss | 2    |   22 |
+----+------+------+------+
3 rows in set (0.00 sec)

slave
mysql> select * from user;
+----+------+------+------+
| id | name | sex  | age  |
+----+------+------+------+
|  2 | qqq  | 2    |   22 |
|  3 | ssss | 2    |   22 |
+----+------+------+------+
2 rows in set (0.01 sec)

id=1是配置主从同步前的数据
id=2和3的是配置主从后的数据
```


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

