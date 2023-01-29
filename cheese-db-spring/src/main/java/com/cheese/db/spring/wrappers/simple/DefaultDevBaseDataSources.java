package com.cheese.db.spring.wrappers.simple;

import com.cheese.db.spring.wrappers.DevBaseDataSources;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 多数据源 默认实现
 *
 * @author sobann
 */
public class DefaultDevBaseDataSources implements DevBaseDataSources {

    private Map<String, DataSource> sources = new ConcurrentHashMap<>();

    @Override
    public Map<String, DataSource> getSources() {
        return sources;
    }

    public void setSources(Map<String, DataSource> sources) {
        this.sources = sources;
    }

    @Override
    public void put(String dbKey, DataSource dataSource) {
        this.sources.put(dbKey, dataSource);
    }
}
