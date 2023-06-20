package com.cheese.db.server.spring.wrapper;

import org.apache.ibatis.session.SqlSession;

import java.util.Map;

/**
 * DevBaseSqlSessionTemplate包装
 *
 * @author sobann
 */
public interface DevBaseSqlSessions {

    /**
     * 获取sqlSession列表
     *
     * @return
     */
    Map<String, SqlSession> getSqlSessions();

    /**
     * 存储sqlSession
     *
     * @param dbKey
     * @param sqlSession
     */
    void put(String dbKey, SqlSession sqlSession);
}
