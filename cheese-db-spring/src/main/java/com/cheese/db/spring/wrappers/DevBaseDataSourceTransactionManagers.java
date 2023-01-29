package com.cheese.db.spring.wrappers;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 多数据源事务管理器 - spring
 *
 * @author sobann
 */
public interface DevBaseDataSourceTransactionManagers {

    void put(String dbKey, DataSourceTransactionManager dataSourceTransactionManager);

    DataSourceTransactionManager get(String dbKey);
}
