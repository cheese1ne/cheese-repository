package com.cheese.db.core.condition;


import com.cheese.db.core.enums.ActionType;

/**
 * 条件对象顶层接口
 *
 * @author sobann
 */
public interface Action {

    String getDbKey();

    String getCode();

    ActionType getActionType();
}
