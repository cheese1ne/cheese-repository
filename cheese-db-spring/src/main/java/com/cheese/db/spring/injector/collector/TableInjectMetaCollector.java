package com.cheese.db.spring.injector.collector;

import com.cheese.db.core.support.DevBaseConstant;
import com.cheese.db.spring.injector.metadata.InjectMeta;
import com.cheese.db.spring.support.DevBaseApplicationContextSupport;
import com.cheese.db.spring.support.DevBaseTableMetaSupport;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 抽象的表格元数据收集者
 *
 * @author sobann
 */
public abstract class TableInjectMetaCollector extends DevBaseApplicationContextSupport implements InjectMetaCollector, EnvironmentAware, DevBaseConstant {

    private boolean needLoad;
    private Environment environment;
    private String[] injectSchemas;
    private String[] injectDbKeys;

    @Override
    public List<InjectMeta> getInjectMetas() {
        if (!needLoad || Objects.isNull(injectSchemas) || injectSchemas.length <= DevBaseConstant.ZERO || injectSchemas.length != injectDbKeys.length) {
            return Collections.emptyList();
        }
        for (int i = 0; i < injectSchemas.length; i++) {
            DevBaseTableMetaSupport.bindDBSchema(injectSchemas[i], injectDbKeys[i]);
        }
        List<InjectMeta> injectMetas = new ArrayList<>();
        for (String injectSchema : injectSchemas) {
            final List<InjectMeta> tableMetas = this.getInjectMetas(injectSchema);
            injectMetas.addAll(tableMetas);
        }
        return injectMetas;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        // 不启用时
        final Boolean enable = environment.getProperty("devbase-db.use-default-config", Boolean.class, Boolean.FALSE);
        this.needLoad = enable;
        if (!enable) {
            return;
        }
        final String schemas = environment.getProperty("devbase-db.inject-schemas");
        final String dbKeys = environment.getProperty("devbase-db.inject-db-keys");
        if (Objects.isNull(schemas)) {
            this.injectSchemas = new String[0];
        } else {
            this.injectSchemas = StringUtils.split(schemas, ",");
        }
        if (Objects.isNull(dbKeys)) {
            this.injectDbKeys = new String[0];
        } else {
            this.injectDbKeys = StringUtils.split(dbKeys, ",");
        }
    }

    /**
     * 根据数据库名称获取需要注入的表格元数据
     *
     * @param schema
     * @return
     */
    protected abstract List<InjectMeta> getInjectMetas(String schema);

}
