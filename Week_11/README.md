## 作业

### 必做作业1，配置redis的主从复制，sentinel高可用，Cluster集群。

#### 通过docker搭建redis主从
```
作业地址：https://redick01.github.io/redick.github.io/#/blog/%E4%B8%AD%E9%97%B4%E4%BB%B6/redis/redis_1?id=docker%e6%90%ad%e5%bb%baredis%e4%b8%bb%e4%bb%8e%e5%a4%8d%e5%88%b6
```
#### 配置Sentinel
```
作业地址：https://redick01.github.io/redick.github.io/#/blog/%E4%B8%AD%E9%97%B4%E4%BB%B6/redis/redis_1?id=redis-sentinel%ef%bc%88%e5%93%a8%e5%85%b5%ef%bc%89%e9%85%8d%e7%bd%ae
```

## 学习笔记

### 老师分享的

```
Redis 基础手册：https://www.runoob.com/redis/redis-tutorial.html
Redis 基础系列（推荐）：https://www.cnblogs.com/itzhouq/p/redis1.html
Redis常用参数：https://www.cnblogs.com/liufukui/p/10448827.html
Spring Cache整合ehcache和Redis：https://www.cnblogs.com/xiang--liu/p/9720344.html
Spring Data Redis：https://blog.csdn.net/lydms/article/details/105224210
Redis命中率：https://www.cnblogs.com/junlinqunxia/p/11244230.html
Redis知识与场景集合（推荐）：http://c.biancheng.net/view/4560.html
Redis内存优化：https://redis.io/topics/memory-optimization
Redis数据类型原理与优化（推荐）：https://www.cnblogs.com/williamjie/p/11288062.html
Redis优化的几个tips：
- https://zhuanlan.zhihu.com/p/55068567
- http://www.jwsblog.com/archives/50.html

Lettuce详解：
- https://www.cnblogs.com/throwable/p/11601538.html
- https://blog.csdn.net/moonpure/article/details/82658788

Redis设计与实现：http://redisbook.com/
```

1、redis模拟表，最好是以模拟行的方式
比如：
A-USD
A-CNY
B-USD
B-CNY
一个是4个key，另一中方式是json，写到一个key，，，哪个好？
大多数情况用 4个key，因为：
json相当于模拟了一个表
1、序列化
2、非结构化的数据，2个用户x2 account，20万x20万？
3、数据过期，json无法针对具体数据处理

