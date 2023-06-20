package com.cheese.db.common.condition;

import com.cheese.db.common.constant.DevBaseConstant;
import com.cheese.db.common.enums.ActionType;

import java.io.Serializable;

/**
 * 条件对象顶层接口
 *
 * @author sobann
 */
public interface Action extends DevBaseConstant, Serializable {

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
