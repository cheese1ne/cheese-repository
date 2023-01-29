package com.cheese.db.core;

import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 继承自mybatis中MapperRegistry类
 * <p>
 * 暂未使用
 *
 * @author sobann
 */
public class DevBaseMapperRegistry extends MapperRegistry {

    private final Configuration config;
    private final String dbKey;
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    public DevBaseMapperRegistry(String dbKey, Configuration config) {
        super(config);
        this.dbKey = dbKey;
        this.config = config;
    }

    public <T> boolean hasMapper(String dbKey, Class<T> type) {
        return knownMappers.containsKey(type);
    }
}
