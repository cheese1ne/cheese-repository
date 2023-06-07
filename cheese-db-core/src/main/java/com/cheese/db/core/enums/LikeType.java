package com.cheese.db.core.enums;

/**
 * 模糊匹配
 *
 * @author sobann
 */
public enum LikeType {

    /**
     * 左模糊
     */
    LEFT("%%%s"),
    /**
     * 右模糊
     */
    RIGHT("%s%%"),
    /**
     * 全模糊
     */
    ALL("%%%s%%");

    private final String segment;

    LikeType(String segment) {
        this.segment = segment;
    }

    public String getSegment() {
        return segment;
    }
}
