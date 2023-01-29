package com.cheese.db.core.condition;

/**
 * 抽象类
 * 如果有公用的一些方法以及属性可以抽提到此类中
 *
 *
 * tip:
 * @author sobann
 */
public abstract class AbstractAction implements Action {

    private String dbKey;
    private String code;

    @Override
    public String getDbKey() {
        return dbKey;
    }

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class PAGE_CONF {
        public int start;
        public int limit = 10;

        public PAGE_CONF() {
        }

        public PAGE_CONF(int start, int limit) {
            this.start = start;
            this.limit = limit;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }
    }
}
