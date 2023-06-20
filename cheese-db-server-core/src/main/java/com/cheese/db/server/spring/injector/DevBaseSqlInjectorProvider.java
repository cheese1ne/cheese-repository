package com.cheese.db.server.spring.injector;

/**
 * DevBaseSqlInjector 提供者顶层接口
 * sql注入提供者，根据sql注入的方式分别实现
 *
 *
 * @author sobann
 */
public interface DevBaseSqlInjectorProvider {

    /**
     * 获取sql注册器
     *
     * @return
     */
    DevBaseSqlInjector getDevBaseSqlInjector();
}
