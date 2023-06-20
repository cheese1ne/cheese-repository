package com.cheese.db.server.spring.wrapper;

import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Map;

/**
 * 多数据源会话工厂
 *
 * @author sobann
 */
public interface DevBaseSqlSessionFactories {

    /**
     * 获取会话工厂列表
     *
     * @return
     */
    Map<String, SqlSessionFactory> getFactories();

    /**
     * 保存会话工厂
     *
     * @param dbKey
     * @param sqlSessionFactory
     */
    void put(String dbKey, SqlSessionFactory sqlSessionFactory);
}
