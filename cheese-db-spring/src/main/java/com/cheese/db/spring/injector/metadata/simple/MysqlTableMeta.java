package com.cheese.db.spring.injector.metadata.simple;

import com.cheese.db.spring.injector.metadata.TableMeta;

/**
 * 默认的表格元数据收集
 *
 * @author sobann
 */
public class MysqlTableMeta implements TableMeta {

    private String tableSchema;

    private String tableName;

    private String columnName;

    private String columnKey;

    private String dataType;

    @Override
    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    @Override
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

}
