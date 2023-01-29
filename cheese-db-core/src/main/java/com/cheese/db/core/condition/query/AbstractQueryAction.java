package com.cheese.db.core.condition.query;

import com.cheese.db.core.condition.AbstractAction;
import com.cheese.db.core.enums.ActionType;
import com.cheese.db.core.support.DevBaseConstant;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 查询功能
 *
 * @author sobann
 */
public abstract class AbstractQueryAction<T> extends AbstractAction implements DevBaseConstant {

    private String sqlSelect = SQL_ALL;
    private Map<String, Object> param = new HashMap<>();
    /*
        条件传入实体实体,实体的参数最终都需要转入param中
        现阶段select语句script中仅使用#{ew.param.*}作为条件
     */
    private T entity;

    public void setSqlSelect(String sqlSelect) {
        this.sqlSelect = Objects.nonNull(sqlSelect) && !BLANK_STR.equals(sqlSelect) ? sqlSelect : SQL_ALL;
    }

    public String getSqlSelect() {
        return sqlSelect;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    protected Map<String, Object> getParam() {
        return this.param;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {

        this.entity = entity;
    }

    public ActionType getActionType() {
        return ActionType.SELECT;
    }

}
