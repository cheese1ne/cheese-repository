package com.cheese.db.server.core.proxy;

import com.cheese.db.common.condition.Action;
import com.cheese.db.common.condition.manager.DevBaseActionManager;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.SqlSession;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * devbase持久层代理
 *
 * @author sobann
 */
public class DevBaseMapperProxy<T> implements InvocationHandler, Serializable {

    private final Map<String, SqlSession> sqlSessions;
    private final Class<T> mapperInterface;
    private final Map<Method, Map<String, DevBaseDBMapperMethod>> methodCache;
    private final DevBaseActionManager devBaseActionManager;

    public DevBaseMapperProxy(Map<String, SqlSession> sqlSessions, Class<T> mapperInterface,
                              Map<Method, Map<String, DevBaseDBMapperMethod>> methodCache,
                              DevBaseActionManager devBaseActionManager) {
        this.sqlSessions = sqlSessions;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
        this.devBaseActionManager = devBaseActionManager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            } else if (method.isDefault()) {
                return invokeDefaultMethod(proxy, method, args);
            }
        } catch (Throwable t) {
            throw ExceptionUtil.unwrapThrowable(t);
        }

        //获取参数
        Action action = this.obtainActionFromParam(args);
        devBaseActionManager.manager(action);
        final DevBaseDBMapperMethod mapperMethod = cachedMapperMethod(method, action);
        SqlSession currentSqlSession = getCurrentSqlSession(action);
        return mapperMethod.execute(currentSqlSession, args);
    }

    private DevBaseDBMapperMethod cachedMapperMethod(Method method, Action action) {
        //上限根据数据库中配置的sql上限决定
        Map<String, DevBaseDBMapperMethod> devbaseMapperMethodMap = this.methodCache.computeIfAbsent(method, k -> new HashMap<>(256));
        //缓存 method -> code -> MapperMethod
        if (devbaseMapperMethodMap.containsKey(action.getCode())) return devbaseMapperMethodMap.get(action.getCode());
        //根据方法和参数code构造对应的方法 通过msId在configuration中获取到对应的statement,封装参数、返回值、查询类型等内容
        DevBaseDBMapperMethod codeMethod = new DevBaseDBMapperMethod.Builder(mapperInterface, method, action, sqlSessions.get(action.getDbKey()).getConfiguration()).build();
        devbaseMapperMethodMap.put(action.getCode(), codeMethod);
        return codeMethod;
    }

    private SqlSession getCurrentSqlSession(Action action) {
        String dbKey = action.getDbKey();
        return sqlSessions.get(dbKey);
    }

    private Action obtainActionFromParam(Object[] args) {
        return (Action) Arrays.stream(args).filter(item -> Action.class.isAssignableFrom(item.getClass())).findAny().orElse(null);
    }


    private Object invokeDefaultMethod(Object proxy, Method method, Object[] args)
            throws Throwable {
        final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                .getDeclaredConstructor(Class.class, int.class);
        if (!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }
        final Class<?> declaringClass = method.getDeclaringClass();
        return constructor.newInstance(declaringClass,
                MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED |
                        MethodHandles.Lookup.PACKAGE | MethodHandles.Lookup.PUBLIC)
                .unreflectSpecial(method, declaringClass).bindTo(proxy).invokeWithArguments(args);
    }
}
