package com.cheese.db.spring.support;

import com.cheese.db.core.support.DevBaseConstant;
import com.cheese.db.spring.injector.metadata.TableMeta;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 表格元数据处理缓存
 *
 * @author sobann
 */
public class DevBaseTableMetaSupport implements DevBaseConstant {

    private static Map<String, String> DB_SCHEMA = new ConcurrentHashMap<>();
    private static Map<String, TableMeta> TABLE_PRIMARY = new ConcurrentHashMap<>();

    public static void bindDBSchema(String schema, String dbKey) {
        DB_SCHEMA.put(schema, dbKey);
    }

    public static String getDbKey(String schema) {
       return DB_SCHEMA.get(schema);
    }

    public static void bind(String schema, String tableName, TableMeta primaryMeta) {
        TABLE_PRIMARY.put(schema + TOKEN_SEPARATOR + tableName, primaryMeta);
    }

    public static TableMeta obtain(String schema, String tableName) {
        return TABLE_PRIMARY.get(schema + TOKEN_SEPARATOR + tableName);
    }
}
