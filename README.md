## cheese-repository -- 以mybatis为核心的多数据源的持久层操作框架

### 一、架构组件
- cheese-db-core:`完成与mybatis的整合，针对多数据源重新设计代理方法的执行策略；设计条件构建工具`
- cheese-db-rpc(待设计):`微服务之间数据调用方式，当前为单体服务设计功能`
- cheese-db-spring:`cheese-db整合spring，完成持久层实例BeanDefinition的定义、初始化以及持久层代理的创建；多数据源、事务管理器、会话工厂以及会话顶层设计及默认实现；sql注册机以及相关功能设计；多数据源事务处理切面(待完善)`
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
2. 搭建springboot工程，并在启动类上添加`@EnableDevBase`注解（参考cheese-db-sample工程）
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