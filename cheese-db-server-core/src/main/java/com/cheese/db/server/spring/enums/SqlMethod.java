package com.cheese.db.server.spring.enums;

/**
 * 参考mybatis-plus
 *
 * @author sobann
 */
public enum SqlMethod {

    /**
     * 新增
     */
    INSERT("insert", "插入一条数据（选择字段插入）", "INSERT INTO %s %s VALUES %s"),

    /**
     * 删除
     */
    DELETE("delete", "根据 entity 条件删除记录", "DELETE FROM %s %s"),

    /**
     * 修改
     */
    UPDATE("update", "根据条件，更新记录", "UPDATE %s %s %s"),

    /**
     * 查询
     */
    SELECT("selectList", "查询满足条件所有数据", "SELECT %s FROM %s %s");
    

    private final String method;
    private final String desc;
    private final String sql;

    SqlMethod(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }

    public String getSql() {
        return sql;
    }
}
