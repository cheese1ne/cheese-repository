package com.cheese.db.core.enums;

/**
 * 比较枚举
 *
 * @author sobann
 */
public enum Comparator {
    /**
     * 大于
     */
    GT(">"),
    /**
     * 小于
     */
    LT("<"),
    /**
     * 大于等于
     */
    GTE(">="),
    /**
     * 小于等于
     */
    LTE("<="),
    /**
     * 相等
     */
    EQUALS("="),;



    private final String token;

    Comparator(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
