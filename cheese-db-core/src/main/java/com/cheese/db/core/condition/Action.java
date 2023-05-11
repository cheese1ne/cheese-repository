package com.cheese.db.core.condition;


import com.cheese.db.core.enums.ActionType;
import com.cheese.db.core.support.DevBaseConstant;

/**
 * 条件对象顶层接口
 *
 * @author sobann
 */
public interface Action extends DevBaseConstant {

    String getDbKey();

    String getCode();

    ActionType getActionType();
}
