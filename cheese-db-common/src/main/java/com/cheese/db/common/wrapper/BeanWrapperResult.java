package com.cheese.db.common.wrapper;

import com.cheese.db.common.constant.DevBaseConstant;
import com.cheese.db.common.exception.ResultWrapperException;

import java.lang.reflect.Field;

/**
 * 使用实例方式进行包装
 *
 * @param <T> 包装返回的实体类型
 * @param <R> 被包装的最终数据
 *
 * @author sobann
 */
public class BeanWrapperResult<T, R> implements WrapperResult<T, R>, DevBaseConstant {

    private Field resultFiled;
    private final Class<T> beanClass;
    private final T bean;

    public BeanWrapperResult(Class<T> beanClass) {
        try {
            this.beanClass = beanClass;
            //必须提供一个空参的构造方法以及针对result的getSet方法
            this.bean = beanClass.newInstance();
            this.resultFiled = beanClass.getDeclaredField(WRAPPER_RESULT_KEY);
            resultFiled.setAccessible(true);
        } catch (Exception e) {
            throw new ResultWrapperException(e.getMessage());
        }
    }

    @Override
    public WrapperResult<T, R> wrapperResult(R result) {
        //设置属性
        try {
            resultFiled.set(bean, result);
            return this;
        } catch (Exception e) {
            throw new ResultWrapperException(e.getMessage());
        }
    }

    @Override
    public R getResult() {
        //设置属性
        try {
            R result = (R) resultFiled.get(bean);
            return result;
        } catch (Exception e) {
            throw new ResultWrapperException(e.getMessage());
        }
    }

}
