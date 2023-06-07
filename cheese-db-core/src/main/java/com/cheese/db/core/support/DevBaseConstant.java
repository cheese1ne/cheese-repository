package com.cheese.db.core.support;

/**
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
    String DEFAULT_SYS_CONFIG_SQL = "SELECT id, code AS code, dbKey AS dbkey, actiontype AS sqlCommandTypeCode, content AS content FROM sys_sql_config WHERE actiontype IN (0,1,2,3) <if test=\"ew.param.code !=null and ew.param.code != '' \"> AND code = #{ew.param.code}</if>";

    int ZERO = 0;
    int ONE = 1;
    int TWO = 2;
    int THREE = 3;
    int TEN = 10;
    int DEFAULT_CURRENT = ZERO;
    int DEFAULT_SIZE = TEN;

}
