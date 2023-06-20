package com.cheese.db.common.condition.page;

import java.io.Serializable;
import java.util.List;

/**
 * 抽象分页
 *
 * @author sobann
 */
public interface IPage<T> extends Serializable {

    /**
     * 获取数据列表
     *
     * @return
     */
    List<T> getRecords();

    /**
     * 设置数据列表
     *
     * @param records
     */
    void setRecords(List<T> records);

    /**
     * 获取总条数
     *
     * @return
     */
    long getTotal();

    /**
     * 设置总条数
     *
     * @param total
     */
    void setTotal(long total);

    /**
     * 获取每页条数
     *
     * @return
     */
    long getSize();

    /**
     * 设置每页条数
     *
     * @param size
     */
    void setSize(long size);

    /**
     * 获取当前页
     *
     * @return
     */
    long getCurrent();

    /**
     * 设置当前页
     *
     * @param current
     */
    void setCurrent(long current);
}
