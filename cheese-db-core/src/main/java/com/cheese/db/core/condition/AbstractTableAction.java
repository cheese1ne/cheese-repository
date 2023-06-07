package com.cheese.db.core.condition;

/**
 * 关于直接对数据表操作的抽象action
 *
 * @author sobann
 */
public abstract class AbstractTableAction extends AbstractAction{

    private final String tableName;

    public AbstractTableAction(String dbKey, String tableName) {
        super(dbKey, null);
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public String getCode() {
        // dbKey + tableName + ActionType 确认唯一Statement
        return this.getDbKey() + TOKEN_SEPARATOR + this.getTableName() + TOKEN_SEPARATOR + this.getActionType().name();
    }
}
