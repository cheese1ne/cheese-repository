package com.cheese.db.common.wrapper;

/**
 * 包装类顶层接口
 *
 * @param <T> 包装返回的实体类型
 * @param <R> 被包装的最终数据
 * @author sobann
 */
public interface WrapperResult<T, R> {

    /**
     * 获取包装对象,传入result数据进行包装
     *
     * @param result
     * @return
     */
    WrapperResult<T, R> wrapperResult(R result);

    /**
     * 获取包装对象中存放的数据
     *
     * @return
     */
    R getResult();

}
