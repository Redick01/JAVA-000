#### 必做作业1 思考和设计自定义MQ第二个版本或第三个版本，写代码实现其中至少一 个功能点，把设计思路和实现代码，提交到github。

- **版本1，内存Queue**

- [x] 创建内存BlockingQueue，作为底层消息存储 
- [x] 定义Topic，支持多个Topic 
- [x] 定义Producer，支持Send消息 
- [x] 定义Consumer，支持Poll消息

- **版本2，自定义Queue，去掉内存Queue，设计自定义Queue，实现消息确认和消费offset**
- [ ] 自定义内存Message数组模拟Queue。 
- [ ] 使用指针记录当前消息写入位置。
- [ ] 对于每个命名消费者，用指针记录消费位置。

- **版本3，拆分broker和client(包括producer和consumer)**
- [ ] 将Queue保存到web server端
- [ ] 设计消息读写API接口，确认接口，提交offset接口
- [ ] producer和consumer通过httpclient访问Queue
- [ ] 实现消息确认，offset提交
- [ ] 实现consumer从offset增量拉取

第四个版本：功能完善MQ 4、增加多种策略（各条之间没有关系，可以任意选择实现） 1）考虑实现消息过期，消息重试，消息定时投递等策略 2）考虑批量操作，包括读写，可以打包和压缩 3）考虑消息清理策略，包括定时清理，按容量清理、LRU等 4）考虑消息持久化，存入数据库，或WAL日志文件，或BookKeeper 5）考虑将spring mvc替换成netty下的tcp传输协议，rsocket/websocket 完全功能。内存不OOM，持久化。 特别是 走TCP，可以真正做到server->client，从而实现 PUSH模式。
第五个版本：体系完善MQ 5、对接各种技术（各条之间没有关系，可以任意选择实现） 1）考虑封装 JMS 1.1 接口规范 2）考虑实现 STOMP 消息规范 3）考虑实现消息事务机制与事务管理器 4）对接Spring 5）对接Camel或Spring Integration 6）优化内存和磁盘的使用

学习笔记

### 秒杀
1、主动引流
2、从业务层面解决有可能有奇效