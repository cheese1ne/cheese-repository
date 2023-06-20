package com.cheese.db.common.condition;

/**
 * 抽象类
 * 如果有公用的一些方法以及属性可以抽提到此类中
 * tip:
 *
 * @author sobann
 */
public abstract class AbstractAction implements Action {

    private final String dbKey;
    private final String code;

    public AbstractAction(String dbKey, String code) {
        this.dbKey = dbKey;
        this.code = code;
    }

    @Override
    public String getDbKey() {
        return dbKey;
    }

    @Override
    public String getCode() {
        return code;
    }

    public String getSqlSegment() {
        return null;
    }

}
