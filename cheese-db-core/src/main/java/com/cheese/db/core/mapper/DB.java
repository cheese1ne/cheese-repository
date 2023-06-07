package com.cheese.db.core.mapper;

import com.cheese.db.core.condition.Action;
import com.cheese.db.core.condition.page.IPage;
import com.cheese.db.core.support.DevBaseConstant;
import com.cheese.db.core.wrapper.WrapperResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 默认持久层注册实例
 *
 * @author sobann
 */
public interface DB {
    /**
     * 执行action获取结果,此方法为通用方法,
     * String 为
     * 默认result是map时
     *
     * @param action
     * @return
     */
    <R> WrapperResult<Map<String, R>, R> doAction(@Param(DevBaseConstant.ACTION_EW) Action action);

    /**
     * 执行action获取结果
     * 使用实例进行包装
     *
     * @param action
     * @param wrapperClazz
     * @return
     */
    <T, R> WrapperResult<T, R> doAction(@Param(DevBaseConstant.ACTION_EW) Action action, Class<T> wrapperClazz);

    /**
     * 查询元素列表
     *
     * @param action
     * @return
     */
    List<Map<String, Object>> doActionGetList(@Param(DevBaseConstant.ACTION_EW) Action action);

    /**
     * 查询元素列表
     *
     * @param action
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> doActionGetList(@Param(DevBaseConstant.ACTION_EW) Action action, Class<T> clazz);

    /**
     * 查询单个元素
     *
     * @param action
     * @return
     */
    Map<String, Object> doActionGetOne(@Param(DevBaseConstant.ACTION_EW) Action action);

    /**
     * 查询单个元素
     *
     * @param action
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T doActionGetOne(@Param(DevBaseConstant.ACTION_EW) Action action, Class<T> clazz);

    /**
     * 查询元素分页
     *
     * @param action
     * @param page
     * @return
     */
    IPage<Map<String, Object>> doActionGetPage(IPage<Map<String, Object>> page, @Param(DevBaseConstant.ACTION_EW) Action action);

    /**
     * 查询元素分页
     *
     * @param action
     * @param page
     * @param clazz
     * @param <T>
     * @return
     */
    <T> IPage<T> doActionGetPage(IPage<T> page, @Param(DevBaseConstant.ACTION_EW) Action action, Class<T> clazz);
}
