package com.cheese.db.server.spring.wrapper;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 多数据源
 *
 * @author sobann
 */
public interface DevBaseDataSources {

    /**
     * 获取多数据源列表
     *
     * @return
     */
    Map<String, DataSource> getSources();

    /**
     * 存储数据源
     *
     * @param dbKey
     * @param dataSource
     */
    void put(String dbKey, DataSource dataSource);
}
