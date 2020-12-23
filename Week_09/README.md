## 作业

#### 必做作业1，改造rpc框架
##### rpcfx-core
- 1.com.homework.rpc.util包HttpClientUtil.java使用httpclient4实现http客户端，使用线程池异步处理。
- 2.封装RPC异常，com.homework.rpc.exception包自定义RpcfxException。
- 3.com.homework.rpc.loadbalancer包，RibbonLoadBalancer.java实现简单的基于ribbon的负载均衡。
- 4.com.homework.rpc.register包，ZookeeperRegister.java实现基于zk的注册中心。
- 5.去掉provider中Bean的name，RpcfxInvoker实现ApplicationContentAware，从Spring Context中获取bean。
- 6.Rpcfx，实现简单的ByteBuddy字节码增强，代替动态代理

#### 作业2 dubbo+hmily
- 1.dubbo+hmily作业在transaction-homework-hmily工程
- 2.作业刚开始理解错了，一个dubbo工程就可以搞定，一个项目连两个数据库
- 3.作业中直接在sql中做了运算，实际项目应该在程序代码中运算
- 4.程序中应该多些异常处理


## 学习笔记 

#### 柔性事务的本质
乐观锁，假设回滚是个小概率事件

#### TCC事务隔离级别：读未提交（脏读），相当于于在一个数据库操作不同表
#### XA事务隔离级别：读已提交，事务串行化保证的