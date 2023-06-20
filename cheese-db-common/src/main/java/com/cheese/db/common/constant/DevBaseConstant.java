package com.cheese.db.common.constant;

/**
 * 一些常量配置
 *
 * @author sobann
 */
public interface DevBaseConstant {

    String MAPPED_STATEMENT_ID_TEMPLATE = "%s.%s";
    String BLANK_STR = "";
    String SQL_ALL = "*";
    String ACTION_EW = "ew";
    String[] DEFAULT_WRAPPER_RESULT_METHOD_NAMES = new String[]{"doAction"};
    String WRAPPER_RESULT_KEY = "result";
    String SCRIPT_TAG_TEMPLATE = "<script>\n %s \n</script>";
    String DEFAULT_SQL_CONFIG_DATASOURCE = "sys";
    String DEFAULT_SQL_CONFIG_CODE = "SYS_SQL_CONFIG";
    String DEFAULT_MYSQL_SYS_CONFIG_SQL = "SELECT id, code AS code, dbKey AS dbkey, actiontype AS sqlCommandTypeCode, content AS content FROM sys_sql_config WHERE actiontype IN (0,1,2,3,4) <if test=\"ew.param.code !=null and ew.param.code != '' \"> AND code = #{ew.param.code}</if>";
    String TOKEN_SEPARATOR = "#";

    String ALL_IDENTIFIER = "*";
    String DEFAULT_TABLE_META_CONFIG_CODE = "table_meta";
    String DEFAULT_MYSQL_TABLE_META_CONFIG_SQL = "SELECT table_schema AS tableSchema, table_name AS tableName, column_name AS columnName, column_key AS columnKey, data_type As dataType FROM information_schema.columns <where> <if test =\"ew.param.table_schema != null and ew.param.table_schema != ''\"> AND table_schema = #{ew.param.table_schema} </if> <if test =\"ew.param.table_name != null and ew.param.table_name != ''\"> AND table_name = #{ew.param.table_name} </if> </where>";
    String TABLE_SCHEMA_KEY = "table_schema";
    String TABLE_NAME_KEY = "table_name";
    int ZERO = 0;
    int ONE = 1;
    int TWO = 2;
    int THREE = 3;
    int TEN = 10;
    int DEFAULT_CURRENT = ZERO;
    int DEFAULT_SIZE = TEN;

}
