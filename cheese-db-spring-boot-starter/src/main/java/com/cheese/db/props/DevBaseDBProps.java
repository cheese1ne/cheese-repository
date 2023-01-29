package com.cheese.db.props;

import com.cheese.db.core.props.MybatisConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * devbase-db环境配置项
 *
 * @author sobann
 */
@ConfigurationProperties(prefix = "devbase-db")
public class DevBaseDBProps {

    private boolean enabled = true;

    private boolean useDefaultConfig = false;

    private boolean usePageHelper = true;

    /*多数据源下的mybatis配置信息*/
    private Map<String, MybatisConfig> configuration;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isUseDefaultConfig() {
        return useDefaultConfig;
    }

    public void setUseDefaultConfig(boolean useDefaultConfig) {
        this.useDefaultConfig = useDefaultConfig;
    }

    public Map<String, MybatisConfig> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, MybatisConfig> configuration) {
        this.configuration = configuration;
    }

    public boolean isUsePageHelper() {
        return usePageHelper;
    }

    public void setUsePageHelper(boolean usePageHelper) {
        this.usePageHelper = usePageHelper;
    }
}
