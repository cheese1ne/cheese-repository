package com.cheese.db.core.wrapper;

import com.cheese.db.core.support.DevBaseConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回值包装类，默认使用map进行包装
 *
 * @author sobann
 */
public class MapWrapperResult<R> implements WrapperResult<Map<String, R>, R>, DevBaseConstant {

    private MapWrapperResult() {
    }

    private final Map<String, R> wrapper = new HashMap<>();

    @Override
    public WrapperResult<Map<String, R>, R> wrapperResult(R result) {
        this.wrapper.put(WRAPPER_RESULT_KEY, result);
        return this;
    }

    @Override
    public R getResult() {
        return wrapper.get(WRAPPER_RESULT_KEY);
    }

    public static class Builder {
        private static MapWrapperResult wrapperResult = new MapWrapperResult();

        public static MapWrapperResult build() {
            return Builder.wrapperResult;
        }
    }
}
