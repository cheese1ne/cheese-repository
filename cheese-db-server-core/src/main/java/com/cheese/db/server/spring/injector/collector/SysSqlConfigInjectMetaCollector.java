package com.cheese.db.server.spring.injector.collector;

import com.cheese.db.common.condition.Actions;
import com.cheese.db.common.condition.load.LoadAction;
import com.cheese.db.common.constant.DevBaseConstant;
import com.cheese.db.common.exception.StatementNotFoundException;
import com.cheese.db.server.core.mapper.DB;
import com.cheese.db.server.spring.injector.metadata.InjectMeta;
import com.cheese.db.server.spring.support.DevBaseApplicationContextSupport;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * devbase系统配置库的sql收集
 *
 * @author sobann
 */
public class SysSqlConfigInjectMetaCollector extends DevBaseApplicationContextSupport implements InjectMetaCollector, DevBaseConstant {

    private DB db;

    @Override
    public List<InjectMeta> getInjectMetas() {
        if (db == null) {
            db = DevBaseApplicationContextSupport.getBean(DB.class);
        }
        try {
            // 注册行为可以监听ContextRefreshedEvent事件
            LoadAction action = Actions.getLoad(DEFAULT_SQL_CONFIG_DATASOURCE, DEFAULT_SQL_CONFIG_CODE);
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
