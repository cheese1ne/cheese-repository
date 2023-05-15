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
2. 搭建springboot工程，并在启动类上添加`@EnableDevBase`注解
```java
/*
	devbase功能可以兼容DataSourceAutoConfiguration的功能，进行相应的配置即可
*/
@EnableDevBase
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CheeseApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(CheeseApplication.class,args);
    }

    @Resource
    private DB db;
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        LoadAction action = Actions.getLoad(DevBaseConstant.DEFAULT_SQL_CONFIG_DATASOURCE, DevBaseConstant.DEFAULT_SQL_CONFIG_CODE);
        List<InjectMeta> injectMetas = db.doActionGetList(action, InjectMeta.class);
        //默认注入到DB.class中,可根据项目实际持久层mapper实现
        applicationContext.publishEvent(new SqlInjectEvent(this, DB.class, InjectType.ALL, injectMetas));
    }
}
```
3. `application.properties`中配置
```properties
# devbase-db开启开关，默认开启
devbase-db.enabled=true
# devbase-db使用默认配置，默认为false
devbase-db.use-default-config=true
```
4.在`resources\config`目录下创建`db.setting`文件,配置数据源相关信息
```setting
[sys]
url = jdbc:mysql://localhost:3306/sys-db?xxx
username=xxx
password=xxx
[bus]
url = jdbc:mysql://localhost:3306/sys-db?xxx
username=xxx
password=xxx
```
