package com.cheese.db.spring.injector.collector.method;

import com.cheese.db.spring.injector.collector.dialect.DialectType;
import com.cheese.db.spring.injector.metadata.InjectMeta;
import com.cheese.db.spring.injector.metadata.TableMeta;

import java.util.List;

/**
 * 抽象的注入方法
 *
 * @author sobann
 */
public abstract class AbstractMethod {


    /**
     * 构建注入的sql元数据
     *
     * @param dialectType
     * @param schema
     * @param tableName
     * @param tablePrimary
     * @param oneTableMetaList
     * @return
     */
    public abstract InjectMeta buildInjectMeta(DialectType dialectType, String schema, String tableName, TableMeta tablePrimary, List<? extends TableMeta> oneTableMetaList);

}
