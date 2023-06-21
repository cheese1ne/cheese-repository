package com.cheese.db.common.condition.query;

import com.cheese.db.common.enums.LikeType;

import java.io.Serializable;

/**
 * 键值关系like
 *
 * @author sobann
 */
public final class LikeKeyValue implements Serializable {
    private static final long serialVersionUID = -8932613901112509672L;

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
