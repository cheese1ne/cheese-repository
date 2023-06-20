package com.cheese.db.server.spring.injector.collector.method;

import com.cheese.db.server.spring.enums.DialectType;
import com.cheese.db.server.spring.injector.metadata.InjectMeta;
import com.cheese.db.server.spring.injector.metadata.TableMeta;
import com.cheese.db.server.spring.tool.SqlScriptUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 抽象的注入方法
 *
 * @author sobann
 */
public abstract class AbstractMethod {

    /**
     * 构建通用的字段条件
     *
     * @param tableMetaList
     * @return
     */
    protected String makeColumnParamScript(List<? extends TableMeta> tableMetaList) {
        return tableMetaList.stream()
                .map(TableMeta::getColumnName)
                .map(item -> SqlScriptUtils.convertIf(String.format("AND %s=#{ew.param.%s}", item, item), String.format("ew.param.%s != null", item), false))
                .collect(Collectors.joining("\n"));
    }

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
