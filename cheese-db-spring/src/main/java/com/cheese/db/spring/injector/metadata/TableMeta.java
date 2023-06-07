package com.cheese.db.spring.injector.metadata;

/**
 * 表格元数据
 *
 * @author sobann
 */
public interface TableMeta {

    /**
     * 数据库名称
     *
     * @return
     */
    String getTableSchema();

    /**
     * 数据表名称
     *
     * @return
     */
    String getTableName();

    /**
     * 数据字段名称
     *
     * @return
     */
    String getColumnName();

    /**
     * 索引类型
     *
     * @return
     */
    String getColumnKey();

    /**
     * 数据类型
     *
     * @return
     */
    String getDataType();
}
