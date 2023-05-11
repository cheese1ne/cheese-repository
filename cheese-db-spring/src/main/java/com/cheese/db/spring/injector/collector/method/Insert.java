package com.cheese.db.spring.injector.collector.method;

import com.cheese.db.core.enums.ActionType;
import com.cheese.db.core.support.DevBaseConstant;
import com.cheese.db.spring.injector.collector.dialect.DialectType;
import com.cheese.db.spring.injector.metadata.InjectMeta;
import com.cheese.db.spring.injector.metadata.TableMeta;
import com.cheese.db.spring.injector.metadata.simple.DefaultInjectMeta;
import com.cheese.db.spring.support.DevBaseTableMetaSupport;
import com.cheese.db.spring.utils.SqlScriptUtils;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 新增
 *
 * @author sobann
 */
public class Insert extends AbstractMethod {

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
        injectMeta.setCode(dbKey + DevBaseConstant.TOKEN_SEPARATOR + tableName + DevBaseConstant.TOKEN_SEPARATOR + ActionType.INSERT.name());
        injectMeta.setSqlCommandType(SqlCommandType.INSERT);
        injectMeta.setKeyColumn(tablePrimary.getColumnName());
        injectMeta.setDbKey(dbKey);
        String columnScript = oneTableMetaList.stream().map(TableMeta::getColumnName).map(item -> SqlScriptUtils.convertIf(item + ",", String.format("ew.data.%s != null", item), false)).collect(Collectors.joining("\n"));
        String columnTrimScript = SqlScriptUtils.convertTrim(columnScript, "(", ")", null, ",");
        String valueScript = oneTableMetaList.stream().map(TableMeta::getColumnName).map(item -> SqlScriptUtils.convertIf(String.format("#{ew.data.%s},", item), String.format("ew.data.%s != null", item), false)).collect(Collectors.joining("\n"));
        String valueTrimScript = SqlScriptUtils.convertTrim(valueScript, "(", ")", null, ",");

        String content = String.format(SqlMethod.INSERT.getSql(), tableName, columnTrimScript, valueTrimScript);
        injectMeta.setContent(content);
        return injectMeta;
    }
}
