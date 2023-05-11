package com.cheese.db.spring.injector.collector;

import com.cheese.db.core.condition.Actions;
import com.cheese.db.core.condition.load.LoadAction;
import com.cheese.db.core.exception.StatementNotFoundException;
import com.cheese.db.core.mapper.DB;
import com.cheese.db.core.support.DevBaseConstant;
import com.cheese.db.spring.injector.metadata.InjectMeta;
import com.cheese.db.spring.support.DevBaseApplicationContextSupport;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * devbase系统配置库的sql收集
 *
 * @author sobann
 */
public class SysSqlConfigInjectMetaCollector extends DevBaseApplicationContextSupport implements InjectMetaCollector {

    private DB db;

    @Override
    public List<InjectMeta> getInjectMetas() {
        if (db == null) {
            db = DevBaseApplicationContextSupport.getBean(DB.class);
        }
        try {
            // 注册行为可以监听ContextRefreshedEvent事件
            LoadAction action = Actions.getLoad(DevBaseConstant.DEFAULT_SQL_CONFIG_DATASOURCE, DevBaseConstant.DEFAULT_SQL_CONFIG_CODE);
            List<InjectMeta> injectMetas = db.doActionGetList(action, InjectMeta.class);
            if (Objects.isNull(injectMetas)) {
                return Collections.emptyList();
            }
            return injectMetas;
        }catch (StatementNotFoundException | NullPointerException e) {
            return Collections.emptyList();
        }

    }
}
