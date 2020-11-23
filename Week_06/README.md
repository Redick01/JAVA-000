### 作业

#### 必做 作业

##### 用户表
```
create table tb_user_info (
  id bigint unsigned primary key auto_increment comment '用户ID',
  password varchar(16) default null comment '密码',
  username varchar(64) default null comment '用户名',
  user_real_name varchar(256) default null comment '用户实名',-- 加密存储
  user_name_des varchar(64) default '' comment '脱敏用户姓名',
  user_card_no varchar(256) default null comment '用户身份证号',-- 加密存储
  user_card_type varchar(2) default null comment '证件类型 0-身份证 1-护照',
  phone_no varchar(256) default null comment '手机号码', -- 加密存储
  phone_no_des varchar(11) default null comment '用户脱敏手机号',
  nick_name varchar(64) default '' comment '昵称',
  register_timestamp timestamp not null default current_timestamp comment '注册时间', -- 格式为unix时间戳
  register_location varchar(64) default null comment '注册位置',
  login_timestamp timestamp not null default current_timestamp comment '登陆时间', -- 格式为unix时间戳
  login_device_type varchar(64) default null comment '登陆设备类型，电脑端为操作系统，手机端为手机型号',
  ali_uid varchar(64) default null comment '支付宝uid，用于支付宝快捷登录',
  wechat_uid varchar(64) default null comment '微信uid，用于微信快捷登录',
  account_state varchar(2) default '00' comment '账号状态 00-正常 01-锁定',
  sharing_id int(11) not null comment '分片键', -- 用于分表
  unique key `phone_no_unique_idx` (`phone_no`),
  unique key `user_card_no_unique_idx` (`user_card_no`),
  unique key `ali_uid_unique_idx` (`ali_uid`),
  unique key `wechat_uid_unique_idx` (`wechat_uid`),
  key `sharding_id` (`sharing_id`)
) engine =InnoDB auto_increment=1 charset=GBK row_format=compact;
```
##### 订单表
```
create table tb_order(
    id bigint unsigned not null primary key auto_increment comment '主键',
    order_no varchar(64) not null comment '订单号',
    user_id bigint not null comment '用户ID',
    trans_status varchar(2) not null default '00' comment '交易状态 00-初始 01-处理中 02-处理成功 03-处理失败 04-取消订单',
    pay_status varchar(2) not null default '00' comment '支付状态 00-未支付 01-支付成功',
    refund_status varchar(2) comment '退款状态 00-待退款 01-退款中 02-退款成功 03-退款失败 04-取消退款',
    start_refund_timestamp timestamp comment '发起退款时间',
    refund_timestamp timestamp comment '完成退款事件',
    order_type varchar(2) not null default '00' comment '订单类型',
    business_date varchar(8) not null comment '运营日期',
    trans_amount decimal(12,2) comment '交易金额',
    payment_amount decimal(12,2) comment '付款金额',
    payment_timestamp timestamp default null comment '支付时间戳',
    out_trade_no varchar(64) comment '交易流水号',
    pay_channel varchar(4) comment '支付方式',
    pay_channel_name varchar(20) comment '支付方式名称',
    card_owner varchar(64) comment '卡拥有者',
    card_no varchar(64) comment '卡号',
    card_name varchar(64) comment '卡名称',
    create_timestamp timestamp default current_timestamp comment '订单创建时间戳',
    next_process_timestamp timestamp default null comment '下次处理时间',-- 用于重试处理
    next_process_count int(3) default null comment '下次处理次数',-- 用于终止重试处理
    next_refund_timestamp timestamp default null comment '下次处理退款时间',
    next_refund_count int(3) default null comment '下次处理退款次数',
    send_product_time timestamp comment '送货时间',
    send_type varchar(2) comment '送货方式',
    sharding_id int(11) comment '分片键',
    remark text default null comment '订单备注',
    unique key  `order_no_unique_idx` (`order_no`),
    key `user_id_idx` (`user_id`),
    key `trans_status_idx` (`trans_status`),
    key `pay_status_idx` (`pay_status`),
    key `refund_status_idx` (`refund_status`),
    unique key `out_trade_no_unique_idx` (`out_trade_no`),
    key `business_date_idx` (business_date),
    key `next_process_idx` (next_process_timestamp,next_process_count),
    key `next_refund_idx` (next_refund_timestamp,next_refund_count),
    key `sharding_id` (sharding_id)
) engine =InnoDB auto_increment=1 charset=GBK row_format=compact
```
##### 商品表
```
create table tb_goods(
    id bigint unsigned not null auto_increment primary key comment '主键 商品ID',
    goods_name varchar(256) comment '商品名称',
    brand_id bigint unsigned not null comment '品牌ID',
    cate_id  bigint unsigned not null comment '分类ID',
    price bigint unsigned not null comment '商品价格',
    original_price bigint unsigned not null comment '商品原价',
    tags varchar(255) collate utf8mb4_unicode_ci not null comment '商品标签',
    content text collate utf8mb4_unicode_ci not null comment '商品内容',
    summary text collate utf8mb4_unicode_ci not null comment '商品描述',
    is_sale tinyint(4) not null comment '出售状态: 1是0是',
    created_at timestamp not null default current_timestamp comment '创建时间',
    updated_at timestamp not null default current_timestamp comment '更新时间',
    key `brand_id_idx` (brand_id),
    key `cate_id_idx` (cate_id)
)
```

学习笔记

#### 为什么一般单表数据不超过2000万？
和索引的层级有关
mysql索引是B+树，三层的一个B+树影响数据量 1170*1170*16=21902400

第11课参考材料



FunctionInterface的用法

https://www.cnblogs.com/bigbaby/p/12116886.html



Lambda 表达式如何演化，简化代码用法

https://www.zhihu.com/question/20125256/answer/324121308

https://www.cnblogs.com/bigbaby/p/12113741.html



Steam操作

https://www.jianshu.com/p/932ef18941fb

https://www.jianshu.com/p/633f691f9afb

https://developer.ibm.com/zh/articles/j-lo-java8streamapi/



Guava中文教程

http://ifeve.com/google-guava/



编码规范

https://www.sohu.com/a/215755759_820120

https://zhuanlan.zhihu.com/p/87352004



设计模式

https://github.com/me115/design_patterns

https://github.com/quanke/design-pattern-java



单元测试

https://www.zhihu.com/question/27313846/answer/36132954

