package com.cheese.db.server.core.proxy;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 持久层方法元数据封装
 *
 * @author sobann
 */
public class DevBaseDBMethodSignature {

    private final boolean returnsMany;
    private final boolean returnsMap;
    private final boolean returnsVoid;
    private final boolean returnsCursor;
    private final Class<?> returnType;
    private final String mapKey;
    private final Integer resultHandlerIndex;
    private final Integer rowBoundsIndex;
    private final ParamNameResolver paramNameResolver;
    private final boolean needWrapperResult;

    public DevBaseDBMethodSignature(Configuration configuration, Class<?> mapperInterface, Method method, Class<?> type, boolean needWrapperResult) {
        this.needWrapperResult = needWrapperResult;
        this.returnType = type;
        this.returnsVoid = void.class.equals(this.returnType);
        this.returnsMany = (configuration.getObjectFactory().isCollection(this.returnType) || this.returnType.isArray());
        this.returnsCursor = Cursor.class.equals(this.returnType);
        this.mapKey = getMapKey(method);
        this.returnsMap = (this.mapKey != null);
        this.rowBoundsIndex = getUniqueParamIndex(method, RowBounds.class);
        this.resultHandlerIndex = getUniqueParamIndex(method, ResultHandler.class);
        this.paramNameResolver = new ParamNameResolver(configuration, method);
    }

    public Object convertArgsToSqlCommandParam(Object[] args) {
        return paramNameResolver.getNamedParams(args);
    }

    public boolean hasRowBounds() {
        return rowBoundsIndex != null;
    }

    public RowBounds extractRowBounds(Object[] args) {
        return hasRowBounds() ? (RowBounds) args[rowBoundsIndex] : null;
    }

    public boolean hasResultHandler() {
        return resultHandlerIndex != null;
    }

    public ResultHandler extractResultHandler(Object[] args) {
        return hasResultHandler() ? (ResultHandler) args[resultHandlerIndex] : null;
    }

    public String getMapKey() {
        return mapKey;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public boolean returnsMany() {
        return returnsMany;
    }

    public boolean returnsMap() {
        return returnsMap;
    }

    public boolean returnsVoid() {
        return returnsVoid;
    }

    public boolean returnsCursor() {
        return returnsCursor;
    }

    public Boolean getNeedWrapperResult() {
        return needWrapperResult;
    }

    private Integer getUniqueParamIndex(Method method, Class<?> paramType) {
        Integer index = null;
        final Class<?>[] argTypes = method.getParameterTypes();
        for (int i = 0; i < argTypes.length; i++) {
            if (paramType.isAssignableFrom(argTypes[i])) {
                if (index == null) {
                    index = i;
                } else {
                    throw new BindingException(method.getName() + " cannot have multiple " + paramType.getSimpleName() + " parameters");
                }
            }
        }
        return index;
    }

    private String getMapKey(Method method) {
        String mapKey = null;
        if (Map.class.isAssignableFrom(method.getReturnType())) {
            final MapKey mapKeyAnnotation = method.getAnnotation(MapKey.class);
            if (mapKeyAnnotation != null) {
                mapKey = mapKeyAnnotation.value();
            }
        }
        return mapKey;
    }
}