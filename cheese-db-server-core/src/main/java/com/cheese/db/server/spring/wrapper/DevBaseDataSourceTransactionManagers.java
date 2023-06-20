package com.cheese.db.server.spring.wrapper;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 多数据源事务管理器 - spring
 *
 * @author sobann
 */
public interface DevBaseDataSourceTransactionManagers {

    /**
     * 保存数据库标识与数据源事务管理器的关系
     *
     * @param dbKey
     * @param dataSourceTransactionManager
     */
    void put(String dbKey, DataSourceTransactionManager dataSourceTransactionManager);

    /**
     * 根据数据库标识获取数据源事务管理器
     *
     * @param dbKey
     * @return
     */
    DataSourceTransactionManager get(String dbKey);
}
