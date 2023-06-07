package com.cheese.db.core;

import com.cheese.db.core.props.MybatisConfig;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.session.Configuration;

import java.util.Objects;

/**
 * 继承自mybatis中Configuration类
 *
 * @author sobann
 */
public class DevBaseConfiguration extends Configuration {

    protected final DevBaseMapperRegistry mapperRegistry;
    protected Class<? extends KeyGenerator> keyGenerator;
    private String insertKeyProperty;

    private final String dbKey;

    public DevBaseConfiguration(String dbKey) {
        this.dbKey = dbKey;
        mapperRegistry = new DevBaseMapperRegistry(dbKey, this);
    }

    public boolean match(String dbKey) {
        return Objects.equals(this.dbKey, dbKey);
    }

    public boolean hasMapper(String dbKey, Class<?> type) {
        return mapperRegistry.hasMapper(dbKey, type);
    }

    public void setMybatisConfig(MybatisConfig mybatisConfig) {
        this.setLogImpl(mybatisConfig.getLogImpl());
        this.setMapUnderscoreToCamelCase(mybatisConfig.isMapUnderscoreToCamelCase());
        this.setCacheEnabled(mybatisConfig.isCacheEnabled());
        this.setUseGeneratedKeys(mybatisConfig.isUseGeneratedKeys());
        this.keyGenerator = mybatisConfig.getKeyGenerator() == null ? Jdbc3KeyGenerator.class : mybatisConfig.getKeyGenerator();
        this.insertKeyProperty = mybatisConfig.getInsertKeyProperty() == null ? "id" : mybatisConfig.getInsertKeyProperty();
    }

    public Class<? extends KeyGenerator> getKeyGenerator() {
        return keyGenerator;
    }

    public void setKeyGenerator(Class<? extends KeyGenerator> keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    public String getInsertKeyProperty() {
        return insertKeyProperty;
    }

    public void setInsertKeyProperty(String insertKeyProperty) {
        this.insertKeyProperty = insertKeyProperty;
    }
}
