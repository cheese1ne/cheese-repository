package com.cheese.db.core.proxy;

import com.cheese.db.core.condition.Action;
import com.cheese.db.core.condition.page.IPage;
import com.cheese.db.core.support.ConfigurationSupport;
import com.cheese.db.core.support.DevBaseConstant;
import com.cheese.db.core.wrapper.BeanWrapperResult;
import com.cheese.db.core.wrapper.MapWrapperResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * devbase基于方法级别的代理
 *
 * @author sobann
 */
public class DevBaseDBMapperMethod implements DevBaseConstant {

    private final DevBaseDBSqlCommand command;
    private final DevBaseDBMethodSignature method;

    public DevBaseDBMapperMethod(DevBaseDBSqlCommand command, DevBaseDBMethodSignature method) {
        this.command = command;
        this.method = method;
    }

    /**
     * 根据sqlSession实例和参数
     *
     * @param sqlSession
     * @return
     */
    public Object execute(SqlSession sqlSession, Object[] args) {
        Object result;
        switch (command.getType()) {
            //目前INSERT、UPDATE、DELETE、SELECT都可在doAction通用方法中进行操作，通用方法使用WrapperResult进行封装返回
            case INSERT: {
                Object param = method.convertArgsToSqlCommandParam(args);
                result = rowCountResult(sqlSession.insert(command.getName(), param));
                break;
            }
            case UPDATE: {
                Object param = method.convertArgsToSqlCommandParam(args);
                result = rowCountResult(sqlSession.update(command.getName(), param));
                break;
            }
            case DELETE: {
                Object param = method.convertArgsToSqlCommandParam(args);
                result = rowCountResult(sqlSession.delete(command.getName(), param));
                break;
            }
            case SELECT:
                Object param = method.convertArgsToSqlCommandParam(args);
                if (method.returnsVoid() && method.hasResultHandler()) {
                    result = null;
                } else if (method.returnsMany()) {
                    result = sqlSession.selectList(command.getName(), param);
                } else {
                    //修改了此处 分页查询
                    if (IPage.class.isAssignableFrom(method.getReturnType()) && args != null
                            && IPage.class.isAssignableFrom(args[0].getClass())) {
                        IPage<?> page = (IPage<?>)args[0];
                        //fixme 此处分页写死了 必须使用PageHelper
                        Page<?> pageCondition = PageHelper.startPage((int)page.getCurrent(), (int)page.getSize());
                        page.setRecords(sqlSession.selectList(command.getName(), param));
                        page.setTotal(pageCondition.getTotal());
                        result = page;
                    }else {
                        //查询单个
                        result = sqlSession.selectOne(command.getName(), param);
                    }
                }
                break;
            case FLUSH:
                result = sqlSession.flushStatements();
                break;
            default:
                throw new BindingException("Unknown execution method for: " + command.getName());
        }
        if (result == null && method.getReturnType().isPrimitive() && !method.returnsVoid()) {
            throw new BindingException("Mapper method '" + command.getName()
                    + " attempted to return null from a method with a primitive return type (" + method.getReturnType() + ").");
        }
        //返回数据是否需要使用WrapperResult进行包装
        if (method.getNeedWrapperResult()) {
            /*
                参数未传递wrapperClazz时,使用默认的MapWrapperResult包装参数
                tip: 传入自定义BeanWrapper时，对象中必须包含一个合适的result成员变量以及提供空参数构造方式以及相应的getSet方法
             */
            Object wrapperClazz = Arrays.stream(args).filter(arg -> arg instanceof Class).findFirst().orElse(null);
            if (!Objects.isNull(wrapperClazz)) {
                //使用beanWrapper
                BeanWrapperResult beanWrapperResult = new BeanWrapperResult((Class) wrapperClazz);
                result = beanWrapperResult.wrapperResult(result);
            } else {
                MapWrapperResult wrapperResult = MapWrapperResult.Builder.build();
                result = wrapperResult.wrapperResult(result);
            }
        }
        return result;
    }

    private Object rowCountResult(int rowCount) {
        final Object result;
        if (method.returnsVoid()) {
            result = null;
        } else if (Integer.class.equals(method.getReturnType()) || Integer.TYPE.equals(method.getReturnType())) {
            result = rowCount;
        } else if (Long.class.equals(method.getReturnType()) || Long.TYPE.equals(method.getReturnType())) {
            result = (long) rowCount;
        } else if (Boolean.class.equals(method.getReturnType()) || Boolean.TYPE.equals(method.getReturnType())) {
            result = rowCount > 0;
        } else {
            throw new BindingException("Mapper method '" + command.getName() + "' has an unsupported return type: " + method.getReturnType());
        }
        return result;
    }


    /**
     * DevBaseDBMapperMethod方法建造者
     *
     * @author sobann
     */
    public static class Builder extends ConfigurationSupport {

        private final Class<?> mapperInterface;
        private final Method method;
        private final Action action;
        private final Configuration config;

        public Builder(Class<?> mapperInterface, Method method, Action action, Configuration config) {
            this.mapperInterface = mapperInterface;
            this.method = method;
            this.action = action;
            this.config = config;
        }

        public DevBaseDBMapperMethod build() {
            //判断当前method是否是 DevBaseConstant.DEFAULT_WRAPPER_RESULT_METHOD_NAMES包含的方法，如果是，则将DevBaseDBMethodSignature.needWrapperResult设置为true
            boolean needWrapperResult = resolveNeedWrapperResult(method);
            //注入的statement
            MappedStatement mappedStatement = resolveMappedStatement(mapperInterface, action.getCode(), method.getDeclaringClass(), config);
            DevBaseDBSqlCommand command = new DevBaseDBSqlCommand(mappedStatement.getId(), mappedStatement.getSqlCommandType());
            //returnType从配置的resultMap中获取,默认为Map
            Class<?> returnType = null;
            //非包装类方法当前的returnType根据mappedStatement类型判断
            if (!needWrapperResult){
                returnType = resolveReturnType(method, mapperInterface);
                if (returnType == null) {
                    //解析失败时，直接是用注册时元素的类型
                    returnType = getResultMapClassType(mappedStatement);
                }
            }else {
                returnType = resolveReturnType(mappedStatement);
            }
            DevBaseDBMethodSignature signature = new DevBaseDBMethodSignature(config, mapperInterface, method, returnType, needWrapperResult);
            //被建造的代理方法
            return new DevBaseDBMapperMethod(command, signature);
        }


    }
}
