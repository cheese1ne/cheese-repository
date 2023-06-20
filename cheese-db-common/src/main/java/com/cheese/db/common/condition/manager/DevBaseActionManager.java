package com.cheese.db.common.condition.manager;

import com.cheese.db.common.condition.Action;

/**
 * 默认的Action对象参数管理器
 * 通过对DevBaseActionManager的定义，可以完成:
 * 1.action的校验
 * 2.action增强 ===> 实现通用字段的注入等
 *
 * @author sobann
 */
public interface DevBaseActionManager {

    /**
     * action管理 增强
     *
     * @param action
     */
    void manager(Action action);
}
