package com.cheese.db.core.condition.query;

import com.cheese.db.core.enums.LikeType;

/**
 * 键值关系like
 *
 * @author sobann
 */
public final class LikeKeyValue {

    private String key;

    private Object value;

    private LikeType likeType;

    public LikeKeyValue(String key, Object value, LikeType likeType) {
        this.key = key;
        this.value = value;
        this.likeType = likeType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public LikeType getLikeType() {
        return likeType;
    }

    public void setLikeType(LikeType likeType) {
        this.likeType = likeType;
    }
}
