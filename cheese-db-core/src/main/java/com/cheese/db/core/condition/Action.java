package com.cheese.db.core.condition;


import com.cheese.db.core.enums.ActionType;
import com.cheese.db.core.support.DevBaseConstant;

/**
 * 条件对象顶层接口
 *
 * @author sobann
 */
public interface Action extends DevBaseConstant {

    /**
     * 数据库标识
     *
     * @return
     */
    String getDbKey();

    /**
     * 可以理解为唯一名称空间
     *
     * @return
     */
    String getCode();

    /**
     * 操作类型
     *
     * @return
     */
    ActionType getActionType();
}
