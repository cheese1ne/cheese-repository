package com.cheese.db.spring.injector;


import com.cheese.db.spring.wrappers.DevBaseSqlSessions;

/**
 * sql注册器顶层接口
 *
 * @author sobann
 */
public interface DevBaseSqlInjector {

    void inspectInject(DevBaseSqlSessions devBaseSqlSessions, Class<?> type) throws Exception;
}
