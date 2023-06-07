package com.cheese.db.core.enums;

/**
 * 范围枚举
 *
 * @author sobann
 */
public enum RangeType {

    /**
     * 大于
     */
    IN("IN (%s)"),
    /**
     * 小于
     */
    NOT_IN("NOT IN (%s)");


    private final String segment;

    RangeType(String segment) {
        this.segment = segment;
    }

    public String getSegment() {
        return segment;
    }
}
