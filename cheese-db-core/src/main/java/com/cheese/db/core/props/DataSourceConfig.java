package com.cheese.db.core.props;

import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author sobann
 */
public class DataSourceConfig {

    private Class<? extends DataSource> datasourceType;
    private String url;
    private String driverClassName;
    private String username;
    private String password;
    private String schemeName;
    private String schemeKey;
    private boolean autoLoad = true;

    private int initialSize = 0;
    private int maxActive = 8;
    private int minIdle = 0;
    private int maxIdle = 8;
    private long maxWait = -1;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public Class<? extends DataSource> getDatasourceType() {
        return datasourceType;
    }

    public void setDatasourceType(Class<? extends DataSource> datasourceType) {
        this.datasourceType = datasourceType;
    }

    public boolean isAutoLoad() {
        return autoLoad;
    }

    public void setAutoLoad(boolean autoLoad) {
        this.autoLoad = autoLoad;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getSchemeKey() {
        return schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }
}
