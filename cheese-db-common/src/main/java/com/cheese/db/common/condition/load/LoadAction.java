package com.cheese.db.common.condition.load;

import com.cheese.db.common.condition.AbstractAction;
import com.cheese.db.common.enums.ActionType;

import java.util.HashMap;
import java.util.Map;

/**
 * load
 *
 * @author sobann
 */
public class LoadAction extends AbstractAction {

    private Long id;

    /**
     * 此属性为修改和新增准备
     */
    private Map<String, Object> data;
    /**
     * 此属性作为所有sql的条件属性
     */
    private Map<String, Object> param;

    public LoadAction(String dbKey, String code){
        super(dbKey, code);
        this.data = new HashMap<>(8);
        this.param = new HashMap<>(8);
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    public void putData(String field, Object val) {
        this.data.put(field, val);
    }

    public void putParam(String field, Object condition) {
        this.param.put(field, condition);
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.LOAD;
    }
}
