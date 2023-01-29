package com.cheese.db.spring.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * spring 上下文感知
 *
 * @author sobann
 */
public abstract class DevBaseApplicationContextSupport implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T) applicationContext.getBean(clazz);
    }

    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    public static <T> Map<String, T> getBeans(Class<T> clazz) {
        return BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, clazz, true, false);
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(DevBaseApplicationContextSupport.applicationContext == null) {
            DevBaseApplicationContextSupport.applicationContext = applicationContext;
        }
    }
}
