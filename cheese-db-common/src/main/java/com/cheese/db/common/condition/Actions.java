package com.cheese.db.common.condition;

import com.cheese.db.common.condition.load.LoadAction;

/**
 * 构建Action实例的工厂
 *
 * @author sobann
 */
public class Actions {

    public static LoadAction getLoad(String dbKey, String code) {
        return new LoadAction(dbKey, code);
    }

}
