package com.cheese.db.server.core.proxy;

import com.cheese.db.common.condition.manager.DevBaseActionManager;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * devbase持久层代理生成工厂
 *
 * @author sobann
 */
public class DevBaseMapperProxyFactory<T> {

    private final Class<T> mapperInterface;
    private final Map<String, SqlSession> sqlSessions;
    private final DevBaseActionManager devBaseActionManager;
    private Map<Method, Map<String, DevBaseDBMapperMethod>> methodCache = new ConcurrentHashMap<>();

    public DevBaseMapperProxyFactory(Class<T> mapperInterface, Map<String, SqlSession> sqlSessions, DevBaseActionManager devBaseActionManager) {
        this.mapperInterface = mapperInterface;
        this.sqlSessions = sqlSessions;
        this.devBaseActionManager = devBaseActionManager == null ? action -> {} : devBaseActionManager;
    }

    @SuppressWarnings("unchecked")
    protected T newInstance(DevBaseMapperProxy<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }

    public T newInstance() {
        final DevBaseMapperProxy<T> mapperProxy = new DevBaseMapperProxy<>(sqlSessions, mapperInterface, methodCache, devBaseActionManager);
        return newInstance(mapperProxy);
    }
}
