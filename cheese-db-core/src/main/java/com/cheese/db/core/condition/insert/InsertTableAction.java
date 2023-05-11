package com.cheese.db.core.condition.insert;

import com.cheese.db.core.condition.AbstractTableAction;
import com.cheese.db.core.enums.ActionType;

import java.util.HashMap;
import java.util.Map;

/**
 * 主键自增 使用Jdbc3KeyGenerator存在的问题
 * 1.数据表主键字段必须使用自增策略
 * 2.需要使用明确的TypeHandler来映射主键和数据库字段的关系，比如当前类中插入主键使用字段primary，java类型为Long，使用的字段处理器为LongTypeHandler (尝试使用Serializer 需要自定义一个 TypeHandler 否则会让主键无法回填哦)
 *
 * @author sobann
 */
public class InsertTableAction extends AbstractTableAction {

    private Map<String, Object> data;
    private Long id;

    public InsertTableAction(String dbKey, String tableName) {
        super(dbKey, tableName);
        this.data = new HashMap<>(8);
    }


    public Map<String, Object> getData() {
        return this.data;
    }

    public void putData(String field, Object value) {
        this.data.put(field, value);
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.INSERT;
    }
}
