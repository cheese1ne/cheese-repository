package com.cheese.db.spring.wrappers.simple;

import com.cheese.db.spring.wrappers.DevBaseDataSourceTransactionManagers;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 多数据源事务管理器 - 默认实现
 *
 * @author sobann
 */
public class DefaultDevBaseDataSourceTransactionManagers implements DevBaseDataSourceTransactionManagers {

    private Map<String, DataSourceTransactionManager> dataSourceTransactionManagerMap = new ConcurrentHashMap<>();

    public Map<String, DataSourceTransactionManager> getDataSourceTransactionManagerMap() {
        return dataSourceTransactionManagerMap;
    }

    public void setDataSourceTransactionManagerMap(Map<String, DataSourceTransactionManager> dataSourceTransactionManagerMap) {
        this.dataSourceTransactionManagerMap = dataSourceTransactionManagerMap;
    }

    @Override
    public void put(String dbKey, DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManagerMap.put(dbKey, dataSourceTransactionManager);
    }

    @Override
    public DataSourceTransactionManager get(String dbKey) {
        DataSourceTransactionManager transactionManager = this.dataSourceTransactionManagerMap.getOrDefault(dbKey, null);
        Assert.notNull(transactionManager, "error db key for transactionManager");
        return transactionManager;
    }

}
