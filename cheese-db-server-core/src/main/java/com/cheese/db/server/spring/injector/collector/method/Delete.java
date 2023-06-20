package com.cheese.db.server.spring.injector.collector.method;

import com.cheese.db.common.constant.DevBaseConstant;
import com.cheese.db.common.enums.ActionType;
import com.cheese.db.server.spring.enums.DialectType;
import com.cheese.db.server.spring.enums.SqlMethod;
import com.cheese.db.server.spring.injector.metadata.InjectMeta;
import com.cheese.db.server.spring.injector.metadata.TableMeta;
import com.cheese.db.server.spring.injector.metadata.simple.DefaultInjectMeta;
import com.cheese.db.server.spring.support.DevBaseTableMetaSupport;
import com.cheese.db.server.spring.tool.SqlScriptUtils;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.List;

/**
 * 删除
 *
 * @author sobann
 */
public class Delete extends AbstractMethod implements DevBaseConstant {

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
        if (tablePrimary == null) {
            return null;
        }
        final String dbKey = DevBaseTableMetaSupport.getDbKey(schema);
        // mysql中主键key字段columnKey值为PRI
        DefaultInjectMeta injectMeta = new DefaultInjectMeta();
        injectMeta.setCode(dbKey + TOKEN_SEPARATOR + tableName + TOKEN_SEPARATOR + ActionType.DELETE.name());
        injectMeta.setSqlCommandType(SqlCommandType.DELETE);
        injectMeta.setDbKey(dbKey);
        String columnScript = this.makeColumnParamScript(oneTableMetaList);
        String segmentScript = SqlScriptUtils.convertIf("${ew.sqlSegment}", "ew.sqlSegment != null", false);
        String columnWhereScript = SqlScriptUtils.convertWhere(columnScript + "\n" + segmentScript);
        String content = String.format(SqlMethod.DELETE.getSql(), tableName, columnWhereScript);
        injectMeta.setContent(content);
        return injectMeta;
    }
}