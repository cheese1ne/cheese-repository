package com.cheese.db.core.condition;

import com.cheese.db.core.condition.load.LoadAction;
import com.cheese.db.core.condition.simple.query.DefaultMapQueryAction;

/**
 * 构建Action实例的工厂
 *
 * @author sobann
 */
public class Actions {

    public static LoadAction getLoad(String dbKey,String code){
        return new LoadAction(dbKey, code);
    }

    public static DefaultMapQueryAction getSelect(String dbKey, String tableName){
        return new DefaultMapQueryAction(dbKey, tableName);
    }
}
