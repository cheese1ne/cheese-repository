package com.cheese.db.server.spring.wrapper.simple;

import com.cheese.db.server.spring.wrapper.DevBaseSqlSessions;
import org.apache.ibatis.session.SqlSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DevBaseSqlSessionTemplate包装
 *
 * @author sobann
 */
public class DefaultDevBaseSqlSessions implements DevBaseSqlSessions {

    private Map<String, SqlSession> sqlSessions = new ConcurrentHashMap<>();

    @Override
    public Map<String, SqlSession> getSqlSessions() {
        return sqlSessions;
    }

    public void setSqlSessions(Map<String, SqlSession> sqlSessions) {
        this.sqlSessions = sqlSessions;
    }

    @Override
    public void put(String dbKey, SqlSession sqlSession) {
        this.sqlSessions.put(dbKey, sqlSession);
    }
}
