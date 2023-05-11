package com.cheese.db.core.condition.simple.query;

import java.util.Map;

/**
 * 默认的查询构建器
 *
 * @author sobann
 */
public class DefaultMapQueryAction extends AbstractQueryAction<Map<String, Object>>{

    public DefaultMapQueryAction(String dbKey, String tableName) {
        super(dbKey, tableName);
    }
}
