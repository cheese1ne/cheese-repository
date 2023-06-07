package com.cheese.db.spring.datasource;

import com.cheese.db.core.props.DataSourceConfig;

import java.util.Map;

/**
 * 数据源上下文的内容，包装DataSourceConfig
 *
 * @author sobann
 */
public class DatasourceContext {

    private String configDataSource;

    private Map<String, DataSourceConfig> dataSources;

    public String getConfigDataSource() {
        return configDataSource;
    }

    public void setConfigDataSource(String configDataSource) {
        this.configDataSource = configDataSource;
    }

    public Map<String, DataSourceConfig> getDataSources() {
        return dataSources;
    }

    public void setDataSources(Map<String, DataSourceConfig> dataSources) {
        this.dataSources = dataSources;
    }
}
