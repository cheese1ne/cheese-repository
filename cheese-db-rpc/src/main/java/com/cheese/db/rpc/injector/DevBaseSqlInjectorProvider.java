package com.cheese.db.rpc.injector;

import com.cheese.db.spring.injector.DevBaseSqlInjector;

/**
 * DevBaseSqlInjector 提供者顶层接口
 * sql注入提供者，根据sql注入的方式分别实现
 * 1.单体项目 基于事件的形式进行注册(使用spring广播器进行注册和监听)
 * 2.微服务架构 两种解决思路，
 *      2.1 专门使用某一个服务进行注册:其他服务只需要调用此服务(微服务调用),通过节点增加负载,注册信息改变时集群不受影响(只要注册节点重新注册即可)
 *      2.2 每一个服务都需要注册:需要定义一个注册服务用来管理集群上最新的注册信息,集群以及节点之间信息的同步使用rpc或dubbo的形式完成
 *
 *
 * @author sobann
 */
public interface DevBaseSqlInjectorProvider {

    DevBaseSqlInjector getDevBaseSqlInjector();
}
