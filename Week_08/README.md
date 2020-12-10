### 作业

#### 必做作业1 分库分表增删改查练习
```
作业工程见：sharding-database-table-homework
1.shardingsphere分库分表配置文件见classpath:application.yml
2.工程结构
（1）ShardingApplication.java程序启动类
（2）biz业务包，包括mybatis的mapper包，自测用的controller，已经service包，entity实体类包
（3）config包，核心的数据源配置包，包括sharding数据源配置，分库分票规则配置，SqlSessionFactory配置等
```
#### 必做作业2 使用Hmily实现分布式事务
```
使用Hmily+dubbo实现分布式事务
1、account-service 账户服务
2、order-service 订单服务
3、stock-service 库存服务

演示结果：
1.分别启动三个服务，order-service提供测试的controller

2.请求http://127.0.0.1:8084/order/addOrder接口
参数：{
       "userId":"10000",
       "productId":"1",
       "productCount":"1",
       "totalAmount":100
   }

3.结果：操作订单和库存
库存日志结果：
2020-12-11 03:24:56.095  INFO 6460 --- [ecutorHandler-6] com.stock.impl.StockServiceImpl          : ===================>confirm开始执行
2020-12-11 03:24:56.098  INFO 6460 --- [ecutorHandler-6] com.stock.impl.StockServiceImpl          : ===================>confirm执行完毕

库存数据：1	1	999999	0 库存由 1000000 减少为999999

订单结果：数据库新增一条数据
3	2020-12-11 03:24:56	543995759748632576	0	1	100	1	10000

4.请求http://127.0.0.1:8084/order/payOrder接口
参数：{
       "userId":"10000",
       "orderNo":"543995759748632576"
   }

5.结果：操作订单和账户
操作账户日志：
2020-12-11 03:25:15.820  INFO 6452 --- [ecutorHandler-7] com.account.impl.AccountServiceImpl      : ===================>confirm开始执行
2020-12-11 03:25:15.822  INFO 6452 --- [ecutorHandler-7] com.account.impl.AccountServiceImpl      : ===================>confirm执行结束

操作账户数据：1	10000	9999900	0	2017-09-18 14:54:22	 金额有1000000 变为 9999900

订单结果：更改订单状态，订单状态由0改为1
3	2020-12-11 03:24:56	543995759748632576	1	1	100	1	10000
```

学习笔记