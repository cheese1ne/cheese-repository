package com.cheese.db.core.condition.query;

import com.cheese.db.core.condition.AbstractTableAction;
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
public abstract class AbstractQueryAction<T> extends AbstractTableAction implements DevBaseConstant {

    private String sqlSelect = SQL_ALL;
    private Map<String, Object> param;
    /**
     *  条件传入实体实体,实体的参数最终都需要转入param中
     *  现阶段select语句script中仅使用#{ew.param.*}作为条件
     */
    private T entity;

    public AbstractQueryAction(String dbKey, String tableName) {
        super(dbKey, tableName);
        this.param = new HashMap<>(8);
    }

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

    @Override
    public ActionType getActionType() {
        return ActionType.SELECT;
    }

}
