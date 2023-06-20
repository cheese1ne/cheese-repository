package com.cheese.db.server.core.proxy;


import org.apache.ibatis.mapping.SqlCommandType;

/**
 * mybatis相关statement的msId以及sql类型
 *
 * @author sobann
 */
public class DevBaseDBSqlCommand {

    private final String name;
    private final SqlCommandType type;

    public DevBaseDBSqlCommand(String name, SqlCommandType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public SqlCommandType getType() {
        return type;
    }
}
