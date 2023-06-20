package com.cheese.db.server.spring.bean;

import com.cheese.db.common.condition.manager.DevBaseActionManager;
import com.cheese.db.server.core.proxy.DevBaseMapperProxyFactory;
import com.cheese.db.server.spring.injector.DevBaseSqlInjector;
import com.cheese.db.server.spring.support.DevBaseMapperRegistrySupport;
import com.cheese.db.server.spring.support.DevBaseSqlSessionDaoSupport;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * mybatis与spring框架串联的核心
 * 1.完成持久层实例工厂的创建
 * 2.sqlInjector功能的接入点
 * 3.持久层代理实例的获取
 *
 * @author sobann
 */
public class DevBaseMapperFactoryBean<T> extends DevBaseSqlSessionDaoSupport implements FactoryBean<T> {

    /**
     * 当前注册的工厂实例与Mybatis的不同,通过一个mapper代理多个数据源以及相关的方法
     */
    private Class<T> mapperInterface;

    private boolean addToConfig = true;

    public DevBaseMapperFactoryBean() {
        // intentionally empty
    }

    public DevBaseMapperFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public T getObject() throws Exception {
        return (T) DevBaseMapperRegistrySupport.get(getObjectType()).newInstance();
    }

    public void setMapperInterface(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Class<T> getObjectType() {
        return mapperInterface;
    }

    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {
        //mybatis框架原始：多个MapperProxyFactory使用同一个sqlSessionTemplate
        //DevBase需求：一个MapperProxyFactory代理多个sqlSessionTemplate,发起调用时选择
        super.checkDaoConfig();
        try {
            if (!DevBaseMapperRegistrySupport.hasRegister(mapperInterface)) {
                Map<String, SqlSession> sqlSessionTemplates = this.getDevBaseSqlSessionTemplates().getSqlSessions();
                DevBaseActionManager devBaseActionManager = this.getDevBaseActionManager();
                if (!CollectionUtils.isEmpty(sqlSessionTemplates)) {
                    //创建代理对象
                    DevBaseMapperProxyFactory<T> proxyFactory = new DevBaseMapperProxyFactory<>(mapperInterface, sqlSessionTemplates, devBaseActionManager);
                    DevBaseMapperRegistrySupport.put(mapperInterface, proxyFactory);
                    //注入statement，需要根据数据库中sql的db选项注入到对应的Configuration中
                    DevBaseSqlInjector devBaseSqlInjector = getDevBaseSqlInjector();
                    devBaseSqlInjector.inspectInject(getDevBaseSqlSessionTemplates(), mapperInterface);
                }
                DevBaseMapperRegistrySupport.completeRegister(mapperInterface, true);
            }
        } catch (Exception e) {
            DevBaseMapperRegistrySupport.completeRegister(mapperInterface, false);
            //todo 守护线程后置重新注册
        }
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    /**
     * Return the mapper interface of the MyBatis mapper
     *
     * @return class of the interface
     */
    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    public void setAddToConfig(boolean addToConfig) {
        this.addToConfig = addToConfig;
    }

    /**
     * Return the flag for addition into MyBatis config.
     *
     * @return true if the mapper will be added to MyBatis in the case it is not already registered.
     */
    public boolean isAddToConfig() {
        return addToConfig;
    }
}
