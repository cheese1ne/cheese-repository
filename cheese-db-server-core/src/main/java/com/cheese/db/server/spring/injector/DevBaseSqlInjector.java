package com.cheese.db.server.spring.injector;


import com.cheese.db.server.spring.wrapper.DevBaseSqlSessions;

/**
 * sql注册器顶层接口
 *
 * @author sobann
 */
public interface DevBaseSqlInjector {

    /**
     * sql注册到sqlSession中
     *
     * @param devBaseSqlSessions
     * @param type
     * @throws Exception
     */
    void inspectInject(DevBaseSqlSessions devBaseSqlSessions, Class<?> type) throws Exception;
}
