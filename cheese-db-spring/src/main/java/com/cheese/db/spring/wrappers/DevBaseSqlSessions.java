package com.cheese.db.spring.wrappers;

import org.apache.ibatis.session.SqlSession;

import java.util.Map;

/**
 * DevBaseSqlSessionTemplate包装
 *
 * @author sobann
 */
public interface DevBaseSqlSessions {

    Map<String, SqlSession> getSqlSessions();


    void put(String dbKey, SqlSession sqlSession);
}
