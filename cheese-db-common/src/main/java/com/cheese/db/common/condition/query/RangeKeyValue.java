package com.cheese.db.common.condition.query;

import com.cheese.db.common.enums.RangeType;

/**
 * 范围关系range
 *
 * @author sobann
 */
public class RangeKeyValue {

    private String key;

    private Object[] values;

    private RangeType rangeType;


    public RangeKeyValue(String key, RangeType rangeType, Object... values) {
        this.key = key;
        this.values = values;
        this.rangeType = rangeType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    public RangeType getRangeType() {
        return rangeType;
    }

    public void setRangeType(RangeType rangeType) {
        this.rangeType = rangeType;
    }
}