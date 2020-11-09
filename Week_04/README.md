
## 第七课

### 作业

#### 作业1，思考有多少种方式，在main函数启动一个新线程或线程池，异步运行一个方法，拿到这个方法的返回值后，退出主线程

```
com.thread.homework.Homework03.java
```

#### 作业2，JAVA多线程与并发脑图
```
Java多线程与并发.png
```

### 学习笔记

#### 到底是有锁还是无锁好

1、并发不高，两者差不多
2、并发高一点但是不是特别高，无锁好
3、并发特别高，有锁好

并发特别高的时候，无锁的自旋线程特别多，还不如加锁了

#### 可重入锁

当前线程拿到锁后，后续再次执行获取锁的时候不会重新上锁，而是会使用之前拿到的锁

#### 公平锁

公平锁对每个线程都是公平的，最先排队的线程会拿到锁

#### 非公平锁

非公平锁会随机的将锁给到排队的线程

#### 偏向锁

偏向于之前拿到锁又释放锁的线程

### 第八课

#### JDK基础数据类型与集合及并发问题
```
List：
ArrayList：基于数组，便于按index访问，超过数组需要扩容，扩容成本较高
安全问题：1、写冲突；2、读写冲突，特别是iterator时，整个数据变了，拿到的数据和实际数据不同，或者产生ConcurrentModificationException

LinkedList：使用链表实现，无需扩容
安全问题：1、写冲突:两个写，相互操作冲突；2、读写冲突:读，特别是iterator的时候，数据个数变了 ，拿到了非预期数据或者报错产生ConcurrentModificationException


Vector、Stack
Set：LinkedSet、HashSet、TreeSet
Queue->Deque->LinkedList

Map：HashMap、LinkedHashMap、TreeMap
Dictionary->HashTable->Propertiess
```
#### List线程安全的简单办法
```
1、ArrayList所有操作方法上都加synchronized
2、Collections.synchronizedList，强制将List的操作加上同步
3、Arrays.asList，不允许添加删除，但是可以set替换元素
4、Clooection.unmodifiableList，不允许修改内容，包活增加、删除、set
```
##### CopyOnWriteArrayList
```
核心改进原理：
1、写加锁，保证不会写乱
2、写在一个Copy副本上，而不是原始数据上

思考：
1、插入元素时，在新副本上操作，不影响旧引用
2、读取不需要加锁？
读取的时候是在旧应用上，各个线程拿到的数据相同不会出现不一致情况，并且数据使用volatile，保证新副本替换旧引用时候对各个线程立即可见
```
#### Map

##### HashMap
````
1、基本特点:空间换时间，哈希冲突不大的情况下查找数据性能很高
2、用途:存放指定key的对象，缓存对象 原理:使用hash原理，存k-v数据，
初始容量16，扩容x2，负载因子0.75 JDK8以后，在链表长度到8 & 数组长
度到64时，使用红黑树。

安全问题：
1、写冲突；
2、读写问题，可能造成死循环
多线程进行插入、修改数据时候链表结构可能形成一个环，造成死循环

3、keys()无序问题
````
##### LinkedHashMap
```
1、基本特点:继承自HashMap，对Entry集合添加了一个双向链表
2、用途:保证有序，特别是Java8 stream操作的toMap时使用 
3、原理:同LinkedList，包括插入顺序和访问顺序
```
##### ConcurrentHashMap
```
Java7 实现：
    java7中ConcurrentHashMap使用分段锁思想进行实现，在HashMap原有机构上加一层Segment，先进行hash确定Segment，然后在
确定的Segment的HashMap结构中的操作中加锁，这样其他的Segment不加锁，类似于分库分表思想

Java8实现：
    java8的实现引入了红黑树，CAS等，具体流程要分析源码
```

