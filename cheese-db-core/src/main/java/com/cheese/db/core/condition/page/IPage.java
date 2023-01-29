package com.cheese.db.core.condition.page;

import java.io.Serializable;
import java.util.List;

/**
 * @author sobann
 */
public interface IPage<T> extends Serializable {

    List<T> getRecords();

    void setRecords(List<T> records);

    long getTotal();

    void setTotal(long total);

    long getSize();

    void setSize(long size);

    long getCurrent();

    void setCurrent(long current);
}
