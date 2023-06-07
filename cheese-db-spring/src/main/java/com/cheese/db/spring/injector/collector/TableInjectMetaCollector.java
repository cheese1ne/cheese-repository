package com.cheese.db.spring.injector.collector;

import com.cheese.db.core.props.DataSourceConfig;
import com.cheese.db.core.support.DevBaseConstant;
import com.cheese.db.spring.datasource.DatasourceContext;
import com.cheese.db.spring.injector.metadata.InjectMeta;
import com.cheese.db.spring.support.DatasourceContextSupport;
import com.cheese.db.spring.support.DevBaseApplicationContextSupport;
import com.cheese.db.spring.support.DevBaseTableMetaSupport;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.*;

/**
 * 抽象的表格元数据收集者
 *
 * @author sobann
 */
public abstract class TableInjectMetaCollector extends DevBaseApplicationContextSupport implements InjectMetaCollector, EnvironmentAware, DevBaseConstant {

    private boolean needLoad;
    private final List<String> injectSchemas = new ArrayList<>();

    @Override
    public List<InjectMeta> getInjectMetas() {
        DatasourceContext context = DatasourceContextSupport.support();
        if (!needLoad || Objects.isNull(context) || Objects.isNull(context.getDataSources()) || context.getDataSources().isEmpty()) {
            return Collections.emptyList();
        }
        Collection<DataSourceConfig> dataSourceConfigs = context.getDataSources().values();
        for (DataSourceConfig dataSourceConfig : dataSourceConfigs) {
            DevBaseTableMetaSupport.bindDBSchema(dataSourceConfig.getSchemeName(), dataSourceConfig.getSchemeKey());
            injectSchemas.add(dataSourceConfig.getSchemeName());
        }
        List<InjectMeta> injectMetas = new ArrayList<>();
        for (String injectSchema : injectSchemas) {
            final List<InjectMeta> tableMetas = this.getInjectMetas(injectSchema);
            injectMetas.addAll(tableMetas);
        }
        DatasourceContextSupport.clear();
        return injectMetas;
    }

    @Override
    public void setEnvironment(Environment environment) {
        // 不启用时
        this.needLoad = environment.getProperty("devbase-db.use-default-config", Boolean.class, Boolean.FALSE);
    }

    /**
     * 根据数据库名称获取需要注入的表格元数据
     *
     * @param schema
     * @return
     */
    protected abstract List<InjectMeta> getInjectMetas(String schema);

}
