## 作业

### 必做作业1

redis分布式锁，计数器
jedis实现：https://github.com/Redick01/JAVA-000/blob/main/Week_11/redis-homework/src/main/java/com/homework/redis/JedisUtil.java
redission实现：https://github.com/Redick01/JAVA-000/blob/main/Week_11/redis-homework/src/main/java/com/homework/redis/RedissionUtil.java

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

