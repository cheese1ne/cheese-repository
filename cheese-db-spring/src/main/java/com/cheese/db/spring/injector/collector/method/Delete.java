package com.cheese.db.spring.injector.collector.method;

import com.cheese.db.core.enums.ActionType;
import com.cheese.db.core.support.DevBaseConstant;
import com.cheese.db.spring.injector.collector.dialect.DialectType;
import com.cheese.db.spring.injector.metadata.InjectMeta;
import com.cheese.db.spring.injector.metadata.TableMeta;
import com.cheese.db.spring.injector.metadata.simple.DefaultInjectMeta;
import com.cheese.db.spring.support.DevBaseTableMetaSupport;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.List;

/**
 * @author sobann
 */
public class Delete extends AbstractMethod {

    @Override
    public InjectMeta buildInjectMeta(DialectType dialectType, String schema, String tableName, TableMeta tablePrimary, List<? extends TableMeta> oneTableMetaList) {
        switch (dialectType) {
            case Mysql:
                return this.buildMysqlInjectMeta(schema, tableName, tablePrimary, oneTableMetaList);
            // todo
            case Oracle:
            case Postgres:
            case Sqlserver:
            default:
                return null;
        }
    }


    protected InjectMeta buildMysqlInjectMeta(String schema, String tableName, TableMeta tablePrimary, List<? extends TableMeta> oneTableMetaList) {
        final String dbKey = DevBaseTableMetaSupport.getDbKey(schema);
        // mysql中主键key字段columnKey值为PRI
        DefaultInjectMeta injectMeta = new DefaultInjectMeta();

        injectMeta.setCode(dbKey + DevBaseConstant.TOKEN_SEPARATOR + tableName + ActionType.DELETE.name());
        injectMeta.setSqlCommandType(SqlCommandType.DELETE);
        injectMeta.setDbKey(dbKey);
        //todo
//        injectMeta.setContent();

        return null;
    }
}