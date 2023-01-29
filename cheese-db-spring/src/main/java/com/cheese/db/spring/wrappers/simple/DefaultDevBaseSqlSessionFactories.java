package com.cheese.db.spring.wrappers.simple;

import com.cheese.db.spring.wrappers.DevBaseSqlSessionFactories;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 多数据源会话工厂 默认实现
 *
 * @author sobann
 */
public class DefaultDevBaseSqlSessionFactories implements DevBaseSqlSessionFactories {
    private Map<String, SqlSessionFactory> factoryMap = new ConcurrentHashMap<>();

    @Override
    public Map<String, SqlSessionFactory> getFactoryMap() {
        return factoryMap;
    }

    public void setFactoryMap(Map<String, SqlSessionFactory> factoryMap) {
        this.factoryMap = factoryMap;
    }

    @Override
    public void put(String dbKey, SqlSessionFactory sqlSessionFactory) {
        this.factoryMap.put(dbKey, sqlSessionFactory);
    }
}
