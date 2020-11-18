### 作业

#### 使Java里的动态代理，实现一个简单的AOP com.spring.work.aop包
``
作业见 com.spring.work.aop包，分别有java静态代理，JDK动态代理，CGLib动态代理
``

#### bean装配方式 com.spring.beans包（必做）
```
1、隐式的bean发现机制和自动化装配
在XML文件中配置<context:component-scan base-package="com.spring.beans" />，扫描包下的@Service,@Component,@Autowired注解实现自动化装配

2、Java配置
在配置类ConfigConfiguration类中添加@Configuration注解，表示该类是配置类，使用@Bean注解可以配置Bean

3、XML配置
见spring-config.xml中的setter注入和构造器注入
```

#### 实现一个Spring XML自定义配置，配置一组Bean，例如Student/Klass/School。
``
见spring-config.xml中配置
``

#### 单例各种写法
``
见spring-homewor工程 com.homework.singleton
``

#### 给前面课程提供的Student/Klass/School实现自动配置和Starter。（必做）
``
见spring-boot-starter-homework工程
``

#### 研究一下JDBC接口和数据库连接池，掌握它们的设计和用法:
     1)使用JDBC原生接口，实现数据库的增删改查操作。
     2)使用事务，PrepareStatement方式，批处理方式，改进上述操作。
     3)配置Hikari连接池，改进上述操作。提交代码到Github。（必做）
     作业位置：见jdbc-homework工程
     
#### 挑战作业1 基于AOP和自定义注解，实现MyCache(60)缓存
```
1、使用了Spring的AOP
2、使用了guava的Cache
3、由于时间关系，未实现指定缓存时间的缓，现在默认写后60秒，有时间补上
4、作业参见springboot-homework-project工程
   com.redick.cache.CacheC 是缓存模块
   com.redick.aop 基于Spring AOP实现了"环绕增强"，将接口返回值缓存
   com.redick.annotation 注解
   com.redick.biz.controller 测试Controller
```

### 学习笔记

#### 什么类型循环依赖无法处理
构造函数

#### 一个对象的代理有哪些种类？用在什么场景？
```
1、动态代理
2、静态代理

我理解：用于代码需要增强，并且要增强的代码是通用的，例如：日志、统一异常处理、事务等
```

#### 字节码增强有哪些类似GClib工具
```
jdkProxy
CGlib
Javassist
等
```

#### Bean的加载过程
```
1、调用构造函数：实例化对象
2、看是否依赖其他bean（依赖注入）：设置属性
3、看是否BeanNameAware，有就处理
4、看是否BeanFactoryAware，有就处理
5、ApplicationContextAware：可以通过getBean获取bean
6、BeanPostProcessor前置方法（类似于Filter）：before方法
7、InitializingBean：初始化bean
8、自定义init方法：start
9、BeanPostProcessor后置方法（类似于Filter）：after方法

源码解读：
1、ClassPathXmlApplicationContext构造函数调用refresh()方法
2、执行AbstractApplicationContext的refresh()方法
3、refresh方法中的invokeBeanFactoryPostProcessors步骤，PostProcessorRegistrationDelegate的invokeBeanFactoryPostProcessors方法中,
currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));通过bean工厂创建bean
5、通过AbstractBeanFactory的doGetBean调用到DefaultSingletonBeanRegistry的getSingleton方法，然后通过singletonObject = singletonFactory.getObject();，
调用到AbstractBeanFactory的doGetBean，然后调用到AbstractAutowireCapableBeanFactory的createBean，最后调用到doCreateBean

```

