package com.cheese.db.common.condition.page;

import com.cheese.db.common.constant.DevBaseConstant;

import java.util.Collections;
import java.util.List;

/**
 * IPage默认实现
 *
 * @author sobann
 */
public class DevBasePage<T> implements IPage<T>, DevBaseConstant {

    private List<T> records = Collections.emptyList();
    private long current;
    private long size;
    private long total;

    @Override
    public List getRecords() {
        return this.records;
    }

    @Override
    public void setRecords(List records) {
        this.records = records;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public long getCurrent() {
        //current默认为0
        if (ZERO == this.current) {
            return DEFAULT_CURRENT;
        }
        return this.current;
    }

    @Override
    public void setCurrent(long current) {
        this.current = current;
    }

    @Override
    public long getSize() {
        //size默认为10
        if (ZERO == this.size) {
            return DEFAULT_SIZE;
        }
        return this.size;
    }

    @Override
    public void setSize(long size) {
        this.size = size;
    }
}
