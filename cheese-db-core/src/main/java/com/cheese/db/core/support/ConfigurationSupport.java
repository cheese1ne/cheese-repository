package com.cheese.db.core.support;

import com.cheese.db.core.exception.StatementNotFoundException;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 提供configuration中数据的通用查询功能的类
 *
 * @author sobann
 */
public abstract class ConfigurationSupport implements DevBaseConstant {

    /**
     * MappedStatement 从configuration中获取
     *
     * @param mapperInterface
     * @param methodName
     * @param declaringClass
     * @param configuration
     * @return
     */
    protected MappedStatement resolveMappedStatement(Class<?> mapperInterface, String methodName, Class<?> declaringClass, Configuration configuration) {
        //通过mapperInterface.code确认唯一的MappedStatement
        String statementId = String.format(MAPPED_STATEMENT_ID_TEMPLATE, mapperInterface.getName(), methodName);
        if (configuration.hasStatement(statementId)) {
            return configuration.getMappedStatement(statementId);
        } else if (mapperInterface.equals(declaringClass)) {
            throw new StatementNotFoundException(statementId);
        }

        for (Class<?> superInterface : mapperInterface.getInterfaces()) {
            if (declaringClass.isAssignableFrom(superInterface)) {
                MappedStatement ms = resolveMappedStatement(superInterface, methodName, declaringClass, configuration);
                if (ms != null) {
                    return ms;
                }
            }
        }
        throw new StatementNotFoundException(statementId);
    }


    /**
     * returnType从配置的resultMap中获取,默认为Map
     *
     * @param mappedStatement
     * @return
     */
    protected Class<?> getResultMapClassType(MappedStatement mappedStatement) {
        Class<?> type;
        try {
            List<ResultMap> resultMaps = mappedStatement.getResultMaps();
            if (resultMaps.size() == ONE) {
                type = resultMaps.get(ZERO).getType();
            } else {
                type = resultMaps.get(resultMaps.size() - ONE).getType();
            }
        } catch (Exception e) {
            type = Map.class;
        }
        return type;
    }

    /**
     * 解析返回数据类型
     * 需要根据增删改方法确认的情况
     *
     * @param mappedStatement
     * @return
     */
    protected Class<?> resolveReturnType(MappedStatement mappedStatement) {
        //根据方法返回值获取类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        switch (sqlCommandType) {
            case INSERT:
            case UPDATE:
            case DELETE:
                return Integer.class;
            case SELECT:
                return List.class;
        }
        return null;
    }

    /**
     * 解析返回数据类型
     *
     * @param method
     * @param srcType
     * @return
     */
    protected Class<?> resolveReturnType(Method method, Type srcType) {
        //根据方法返回值获取类型
        try {
            Class<?> returnType;
            Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, srcType);
            if (resolvedReturnType instanceof Class) {
                returnType = (Class) resolvedReturnType;
            } else if (resolvedReturnType instanceof ParameterizedType) {
                returnType = (Class) ((ParameterizedType) resolvedReturnType).getRawType();
            } else {
                returnType = method.getReturnType();
            }
            return returnType;
        } catch (Exception e) {
            //解析失败返回null
            return null;
        }
    }

    /**
     * 是否需要将查询结果包装一次
     *
     * @param method
     */
    protected boolean resolveNeedWrapperResult(Method method) {
        return Arrays.stream(DEFAULT_WRAPPER_RESULT_METHOD_NAMES).anyMatch(item -> Objects.equals(item, method.getName()));
    }
}
