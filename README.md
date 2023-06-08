## cheese-repository -- 以mybatis为核心的多数据源的持久层操作框架

### 一、架构组件
- cheese-db-core:`完成与mybatis的整合，针对多数据源重新设计代理方法的执行策略；设计条件构建工具`
- cheese-db-rpc(待设计):`微服务之间数据调用方式，当前为单体服务设计功能`
- cheese-db-spring:
    - `cheese-db整合spring，完成持久层实例BeanDefinition的定义、初始化以及持久层代理的创建；`
    - `多数据源、事务管理器、会话工厂以及会话顶层设计及默认实现；`
    - `sql注册机以及相关功能设计；`
    - `多数据源事务处理`
- cheese-db-spring-boot-starter:`cheese-db-spring接入springboot，提供可插拔式的组件使用方式`

### 二、架构图
![cheese-db.jpg](https://www.showdoc.com.cn/server/api/attachment/visitFile?sign=f1ed8a078a7e2072f8a2db74734fa32b "[cheese-db.jpg")


### 三、快速开始
1. 引入maven依赖
```xml
<dependency>
    <groupId>com.cheese.db</groupId>
    <artifactId>cheese-db-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```
2. 运行工程下`doc/script/sql`中的`cheese-repository-sys.sql`和`cheese-repository-bus.sql`，目前sql脚本文件只提供mysql
```markdown
cheese-repository-sys.sql 导入配置数据库
cheese-repository-bus.sql 导入其他数据库(可以多个业务数据库，这里提供一个demo)

```

3. 搭建springboot工程，并在启动类上添加`@EnableDevBase`注解（请参考cheese-db-sample工程）
```java
/*
	devbase功能可以兼容DataSourceAutoConfiguration的功能，进行相应的配置即可
*/
@EnableDevBase
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CheeseApplication {
    public static void main(String[] args) {
        SpringApplication.run(CheeseApplication.class,args);
    }

    /**
     * mysql数据库的配置及数据库加载方式
     * 目前仅实现mysql的实现方式，后续会陆续增加pg oracle 等数据库的元数据加载实现
     * 
     * @return
     */
    @Bean
    public MysqlDialectCollector mysqlDialectCollector() {
        return new MysqlDialectCollector();
    }
}
```
3. `application.properties`中配置
```properties
server.port=8081
logging.level.com.cheese.db.spring=debug
spring.main.allow-bean-definition-overriding=true

# devbase 配置
## devbase-db开启开关，默认开启
devbase-db.enabled=true
## devbase-db使用默认配置，默认为false
devbase-db.use-default-config=true

# 多数据源配置
devbase-db.configuration.sys.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
devbase-db.configuration.sys.insert-key-property=id
devbase-db.configuration.bus.insert-key-property=id

## 配置数据源
devbase-db.config-data-source=sys
## 加载数据库的元数据，为数据库下的所有表生成基础增删改的方法
## 元数据加载的key
devbase-db.data-sources.sys.scheme-key=sys
## 元数据加载数据库的名称，与url中的数据库名一致
devbase-db.data-sources.sys.scheme-name=xxx_sys
devbase-db.data-sources.sys.datasource-type=com.alibaba.druid.pool.DruidDataSource
devbase-db.data-sources.sys.url=jdbc:mysql://localhost:3306/xxx_sys?characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8
devbase-db.data-sources.sys.driver-class-name=com.mysql.cj.jdbc.Driver
devbase-db.data-sources.sys.username=root
devbase-db.data-sources.sys.password=root

## 其他数据源 可以配置多个 
devbase-db.data-sources.bus.scheme-key=bus
devbase-db.data-sources.bus.scheme-name=xxx_bus
devbase-db.data-sources.bus.datasource-type=com.alibaba.druid.pool.DruidDataSource
devbase-db.data-sources.bus.url=jdbc:mysql://localhost:3306/xxx_bus?characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8
devbase-db.data-sources.bus.driver-class-name=com.mysql.cj.jdbc.Driver
devbase-db.data-sources.bus.username=root
devbase-db.data-sources.bus.password=root


```

### 四、核心类

#### cheese-db-core
1.`com.cheese.db.core.condition.Action`
```markdown
# 数据库操作的抽象核心，基于Action完成对多数据源的操作
a.LoadAction: 外部配置的行为的专用Action实现
b.AbstractTableAction: 基于数据源加载所有数据源下数据表的增伤改操作，通过此类对应子类实现

```

2.`com.cheese.db.core.proxy.DevBaseMapperProxy`
```markdown
# DevBaseDBMapperMethod完成持久层接口方法DB.class（默认接口）的实现
a.持久层接口的实例（基于JDK.proxy的代理对象）
b.运行时数据源（回话）的选择、方法级别执行器DevBaseDBMapperMethod的创建和缓存
c.接入Action对象参数管理器完成Action的增强（校验或者通用字段的注入）
```

3.`com.cheese.db.core.wrapper.WrapperResult`
```markdown
# 参考mybatis在运行时确认对象类型获取或者设置参数的包装器
a.MapWrapperResult 默认的使用map的包装嘞
b.BeanWrapperResult 实例方式的包装类
```
<hr/>

#### cheese-db-spring

1.`com.cheese.db.spring.annotation.DevBaseMapperRegistrar`
```markdown
# 持久层实例BeanDefinition的收集 这里指定DevBase持久层操作的接口
a.通过DevBaseMappers注入devBaseSqlSessionTemplates、devBaseSqlInjectors、devBaseActionManager实例的名称
b.指定DevBase持久层操作的接口，默认是DB.class
c.通过DevBaseMapperScannerConfigurer完成持久层实例BeanDefinition的元数据收集与注册

```

2.`com.cheese.db.spring.injector.event.DevBaseSqlInjectListener`
```markdown
# 核心的sql注入监听器
a.在Spring容器发布ContextRefreshedEvent事件时，根据InjectMetaCollector拓展收集需要注册的配置以及数据库表的操作
b.监听SqlInjectEvent事件，通过DevBaseSqlInjector完成configuration注册元数据的Statement的操作

```

3.`com.cheese.db.spring.injector.collector.InjectMetaCollector`
```markdown
# 元数据收集器，使用时候实现此接口并将实例交与spring管理即可
a.SysSqlConfigInjectMetaCollector 收集sys_sql_config中的元数据
b.TableInjectMetaCollector 收集数据库表的元数据，目前已实现Mysql元数据的收集
c.数据库表元数据处理通过AbstractMethod的家族完成，这里参考Mybatis-plus

```

4.`com.cheese.db.spring.mapper.DevBaseMapperFactoryBean`
```markdown
# DevBaseMapperFactoryBean是mybatis与spring框架串联的核心
a.完成持久层实例工厂DevBaseMapperProxyFactory的创建（此工厂生产持久层接口的实际执行对象，）
b.sqlInjector功能的接入点（重写DaoSupport.checkDaoConfig, 由Spring完成configuration注册元数据的Statement的操作，元数据这里指sql_config的获取方式、scheme元数据的获取方式）
c.实现FactoryBean.getObject，将持久层实例（其实是个代理对象）的创建权交给Spring管理

```

5.`com.cheese.db.spring.transaction.aop.DevBaseMultiDatasourceTransactionAdvisor`
```markdown
# 基于spring aop，基于注解完成多数据源的事务的控制
通过StaticMethodMatcherPointcutAdvisor接入到Spring，将方法增强与否交给spring判断

```

6.`com.cheese.db.spring.transaction.aspectj.DevBaseMultiDataSourceTransactionalAspect`
```markdown
# 基于aspectj, 基于注解完成多数据源的事务的控制
基于aspectj，比较灵活的事务控制方式

```

7.`com.cheese.db.spring.wrappers.simple.DefaultDevBaseDataSourceTransactionManagers`
```markdown
# 基于DataSourceTransactionManager，使用spring的事务管理器 
spring的事务基于SpringManagedTransaction，mybatis在实际执行的时候会从事务中获取connection, 如果开启了事务connection是储存在TransactionSynchronizationManager中，用的是ThreadLocal

```
<hr/>

#### cheese-db-spring-boot-starter

1.`com.cheese.db.autoconfigure.DevBaseDBAutoConfiguration`
```markdown
# 自动装配类，加载cheese-repository中默认核心实例
DevBaseDataSources: 多数据源
DevBaseDataSourceTransactionManagers: 多数据源事务管理器
DevBaseSqlSessionFactories: 多数据源会话工厂
DevBaseSqlInjectListener: sql注入监听器
DevBaseSqlSessions: 多数据源会话
DevBaseActionManager: Action操作管理器
DevBaseSqlInjector: 默认的sql注入器，提供sys_sql_config的元数据查询功能以及数据库元数据查询功能
SysSqlConfigInjectMetaCollector: sys_sql_config表中元数据的收集器
DevBaseMappersInterceptorProvider: devbase默认分页插件pageHelper的包装类，考虑到考虑devBase和其他持久层框架共同时的隔离处理

```