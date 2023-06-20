package com.cheese.db.server.spring.support;

import com.cheese.db.server.core.proxy.DevBaseMapperProxyFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * devbase与mybatis原始功能不同
 * action中的参数是控制的核心而不是方法入口!!!
 *
 * @author sobann
 */
public class DevBaseMapperRegistrySupport {

    private static Map<Class<?>, Boolean> MAPPER_REGISTER_STATUS = new ConcurrentHashMap<>();
    private static Map<Class<?>, DevBaseMapperProxyFactory<?>> PROXY_FACTORY_CACHE = new ConcurrentHashMap<>();

    public static void put(Class<?> mapperInterface, DevBaseMapperProxyFactory<?> proxyFactory) {
        PROXY_FACTORY_CACHE.put(mapperInterface, proxyFactory);
    }

    public static DevBaseMapperProxyFactory<?> get(Class<?> mapperInterface) {
        DevBaseMapperProxyFactory<?> proxyFactory = PROXY_FACTORY_CACHE.get(mapperInterface);
        return proxyFactory;
    }

    public static boolean hasRegister(Class<?> mapperInterface) {
        return MAPPER_REGISTER_STATUS.getOrDefault(mapperInterface, false);
    }

    public static void completeRegister(Class<?> mapperInterface, boolean mapperLoadStatus) {
        MAPPER_REGISTER_STATUS.put(mapperInterface, mapperLoadStatus);
    }
}
