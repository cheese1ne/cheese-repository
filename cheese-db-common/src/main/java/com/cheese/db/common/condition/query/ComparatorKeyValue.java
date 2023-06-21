package com.cheese.db.common.condition.query;

import com.cheese.db.common.enums.Comparator;

import java.io.Serializable;

/**
 * 键值关系comparator
 *
 * @author sobann
 */
public final class ComparatorKeyValue implements Serializable {
    private static final long serialVersionUID = 1205636860394724252L;

    private String key;

    private Object value;

    private Comparator relation;

    public ComparatorKeyValue(String key, Object value, Comparator relation) {
        this.key = key;
        this.value = value;
        this.relation = relation;
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

    public Comparator getRelation() {
        return relation;
    }

    public void setRelation(Comparator relation) {
        this.relation = relation;
    }
}
