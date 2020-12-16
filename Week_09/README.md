## 作业

#### 作业1
```
rpcfx-core
1.HttpClientUtil.java使用httpclient4实现http客户端，使用线程池异步处理。
2.exception包自定义RpcfxException
3.LocalRegisterCenter.java实现本地注册中心
```

#### 作业2
```
dubbo+hmily作业在transaction-homework-hmily工程
```


## 学习笔记 

#### 柔性事务的本质
乐观锁，假设回滚是个小概率事件

#### TCC事务隔离级别：读未提交（脏读），相当于于在一个数据库操作不同表
#### XA事务隔离级别：读已提交，事务串行化保证的