package com.cheese.db.spring.wrappers;

import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Map;

/**
 * 多数据源会话工厂
 *
 * @author sobann
 */
public interface DevBaseSqlSessionFactories {

    Map<String, SqlSessionFactory> getFactoryMap();

    void put(String dbKey, SqlSessionFactory sqlSessionFactory);
}
