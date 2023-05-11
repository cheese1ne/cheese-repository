package com.cheese.db.spring.injector.collector.method;

import com.cheese.db.spring.injector.collector.dialect.DialectType;
import com.cheese.db.spring.injector.metadata.InjectMeta;
import com.cheese.db.spring.injector.metadata.TableMeta;

import java.util.List;

/**
 * 修改
 *
 * @author sobann
 */
public class Update extends AbstractMethod {

    @Override
    public InjectMeta buildInjectMeta(DialectType dialectType, String schema, String tableName, TableMeta tablePrimary, List<? extends TableMeta> oneTableMetaList) {
        return null;
    }
}
