package com.cheese.db.server.spring.support;

import com.cheese.db.server.core.props.DataSourceConfig;

import java.util.Map;

/**
 * 上下文提供者
 *
 * @author sobann
 */
public class DatasourceContextSupport {

    private static ThreadLocal<DatasourceContext> DATASOURCE_CONTEXT = ThreadLocal.withInitial(DatasourceContext::new);

    /**
     * 提供数据源上下文
     *
     * @return
     */
    public static DatasourceContext support() {
        return DATASOURCE_CONTEXT.get();
    }

    /**
     * 设置数据源上下文
     *
     * @param datasourceContext
     */
    public static void set(DatasourceContext datasourceContext) {
        DATASOURCE_CONTEXT.set(datasourceContext);
    }

    /**
     * 清空当前线程的上下文数据
     */
    public static void clear() {
        DATASOURCE_CONTEXT.remove();
    }


    public static class DatasourceContext {

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
}
