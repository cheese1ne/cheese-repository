## cheese-repository -- 以mybatis为核心的多数据源的持久层操作框架

### 一、架构组件
#### 单体服务
- cheese-db-core:`完成与mybatis的整合，针对多数据源重新设计代理方法的执行策略；设计条件构建工具`
- cheese-db-rpc: (需要拆分cheese-db-spring功能，提供feign、dubbo, 模仿dubbo写一个netty+zookeeper的rpc方式)
- cheese-db-spring:
    - `cheese-db整合spring，完成持久层实例BeanDefinition的定义、初始化以及持久层代理的创建；`
    - `多数据源、事务管理器、会话工厂以及会话顶层设计及默认实现；`
    - `sql注册机以及相关功能设计；`
    - `多数据源事务处理`
- cheese-db-spring-boot-starter:`cheese-db-spring接入springboot，提供可插拔式的组件使用方式`

#### 微服务(服务提供者和消费者组成，支持多种RPC方式)
- cheese-db-common: `基础common，为服务提供者和服务消费者提供顶层设计，如DevBaseService、DevBaseServiceProvider`
- cheese-db-rpc: `提供参数序列化方式(json、hessian2)，支持消费者多种rpc调用方式(feign、dubbo)`
- cheese-db-server-core: `持久层服务提供组件，完成cheese-db持久层数据收集和注册，提供多种服务暴露方式，服务暴露方式要与服务调用方式对应`
- cheese-db-client-core: `持久层服务消费组件，通过rpc组件支持多种服务调用方式，服务调用方式要与服务暴露方式对应`
### 二、架构图
#### 单体服务架构图


#### 微服务架构图


### 三、快速开始
#### 单体服务
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


#### 微服务(这里提供基于ribbon的微服务调用方式)

- 参考分支cheese-db-cloud中cheese-db-sample工程
- 与单体服务不同的部分:
  - 服务提供者依赖使用
    ```xml
     <dependency>
          <groupId>com.cheese.db</groupId>
          <artifactId>cheese-db-server-core</artifactId>
          <version>1.0.0</version>
      </dependency>
    ```
  - 服务提供者配置:
    ```properties
      # -------------------eureka--------------
      spring.application.name=server
      eureka.instance.instance-id=${spring.application.name}:@project.version@-${spring.cloud.client.ip-address}:${server.port}
      eureka.client.service-url.defaultZone=http://localhost:8000/eureka/
      eureka.instance.lease-renewal-interval-in-seconds=5
      eureka.instance.lease-expiration-duration-in-seconds=10
      eureka.client.healthcheck.enabled=false
      eureka.instance.prefer-ip-address=true
      eureka.client.registry-fetch-interval-seconds=5
      ribbon.ServerListRefreshInterval=5000
      # -------------------cheese-db-server--------------
      logging.level.com.cheese.db.server=debug
      spring.main.allow-bean-definition-overriding=true
      
      # devbase 配置
      ## devbase-db开启开关，默认开启
      devbase-db.server.enabled=true
      ## devbase-db使用默认配置，默认为false
      devbase-db.server.use-default-config=true
      ## 事务配置
      devbase-db.server.transaction-type=advisor
      ## 服务暴露方式 默认为feign
      devbase-db.server.exposer-type=default
      
      # 多数据源配置
      devbase-db.server.configuration.sys.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
      devbase-db.server.configuration.sys.insert-key-property=id
      devbase-db.server.configuration.bus.insert-key-property=id
      
      ## 配置数据源
      devbase-db.server.config-data-source=sys
      ## 加载数据库的元数据，为数据库下的所有表生成基础增删改的方法
      ## 元数据加载的key
      devbase-db.server.data-sources.sys.scheme-key=sys
      ## 元数据加载数据库的名称，与url中的数据库名一致
      devbase-db.server.data-sources.sys.scheme-name=db_sys
      devbase-db.server.data-sources.sys.datasource-type=com.alibaba.druid.pool.DruidDataSource
      devbase-db.server.data-sources.sys.url=jdbc:mysql://localhost:3306/db_sys?characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8
      devbase-db.server.data-sources.sys.driver-class-name=com.mysql.cj.jdbc.Driver
      devbase-db.server.data-sources.sys.username=root
      devbase-db.server.data-sources.sys.password=root
      
      ## 其他数据源 可以配置多个
      devbase-db.server.data-sources.bus.scheme-key=bus
      devbase-db.server.data-sources.bus.scheme-name=db_bus
      devbase-db.server.data-sources.bus.datasource-type=com.alibaba.druid.pool.DruidDataSource
      devbase-db.server.data-sources.bus.url=jdbc:mysql://localhost:3306/db_bus?characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=GMT%2B8
      devbase-db.server.data-sources.bus.driver-class-name=com.mysql.cj.jdbc.Driver
      devbase-db.server.data-sources.bus.username=root
      devbase-db.server.data-sources.bus.password=root
    ```
  - 服务消费者依赖使用
    ```xml
      <dependency>
        <groupId>com.cheese.db</groupId>
        <artifactId>cheese-db-client-core</artifactId>
        <version>1.0.0</version>
      </dependency>
    ```
  - 服务消费者配置:
    ```properties
      # -------------------eureka--------------
      spring.application.name=client
      eureka.instance.instance-id=${spring.application.name}:@project.version@-${spring.cloud.client.ip-address}:${server.port}
      eureka.client.service-url.defaultZone=http://localhost:8000/eureka/
      eureka.instance.lease-renewal-interval-in-seconds=5
      eureka.instance.lease-expiration-duration-in-seconds=10
      eureka.client.healthcheck.enabled=false
      eureka.instance.prefer-ip-address=true
      eureka.client.registry-fetch-interval-seconds=5
      ribbon.ServerListRefreshInterval=5000
      # -------------------cheese-db-client--------------
      ## rpc调用的服务名称
      devbase-db.client.rpc-server=server
      ## rpc类型
      devbase-db.client.rpc-type=feign
    ```