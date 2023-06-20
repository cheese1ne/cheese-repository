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
import java.util.stream.Collectors;

/**
 * 修改
 *
 * @author sobann
 */
public class Update extends AbstractMethod {

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
        injectMeta.setCode(dbKey + DevBaseConstant.TOKEN_SEPARATOR + tableName + DevBaseConstant.TOKEN_SEPARATOR + ActionType.UPDATE.name());
        injectMeta.setSqlCommandType(SqlCommandType.UPDATE);
        injectMeta.setDbKey(dbKey);
        String setColumnScript = oneTableMetaList.stream().map(TableMeta::getColumnName).map(item -> SqlScriptUtils.convertIf(String.format("%s=#{ew.data.%s},", item, item), String.format("ew.data.%s != null", item), false)) .collect(Collectors.joining("\n"));
        String whereColumnScript = this.makeColumnParamScript(oneTableMetaList);
        String segmentScript = SqlScriptUtils.convertIf("${ew.sqlSegment}", "ew.sqlSegment != null", false);
        String columnWhereScript = SqlScriptUtils.convertWhere(whereColumnScript + "\n" + segmentScript);
        String columnSetScript = SqlScriptUtils.convertSet(setColumnScript);
        String content = String.format(SqlMethod.UPDATE.getSql(), tableName, columnSetScript, columnWhereScript);
        injectMeta.setContent(content);
        return injectMeta;
    }
}
