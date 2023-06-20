package com.cheese.db.server.spring.registrar;

import com.cheese.db.common.condition.manager.DevBaseActionManager;
import com.cheese.db.server.spring.bean.DevBaseMapperFactoryBean;
import com.cheese.db.server.spring.injector.DevBaseSqlInjector;
import com.cheese.db.server.spring.wrapper.DevBaseSqlSessions;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;

/**
 * 包装DevBaseClassPathMapperScanner完成
 * devbase 持久层接口的beanDefinition的创建
 *
 * @author sobann
 */
public class DevBaseMapperScannerConfigurer implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {

    private boolean processPropertyPlaceHolders;
    private Class<? extends Annotation> annotationClass;
    private Class<?> markerInterface;
    private Class<? extends DevBaseMapperFactoryBean> mapperFactoryBeanClass;
    private String devBaseSqlSessionTemplatesBeanName;
    private String devBaseSqlInjectorsBeanName;
    private String devBaseActionManagerBeanName;
    private String lazyInitialization;
    private String basePackage;


    private boolean addToConfig = true;
    private DevBaseSqlSessions devBaseSqlSessionTemplates;
    private DevBaseSqlInjector devBaseSqlInjectors;
    private DevBaseActionManager devBaseActionManager;


    private ApplicationContext applicationContext;
    private String beanName;

    private BeanNameGenerator nameGenerator;

    public boolean isProcessPropertyPlaceHolders() {
        return processPropertyPlaceHolders;
    }

    public void setProcessPropertyPlaceHolders(boolean processPropertyPlaceHolders) {
        this.processPropertyPlaceHolders = processPropertyPlaceHolders;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public Class<?> getMarkerInterface() {
        return markerInterface;
    }

    public void setMarkerInterface(Class<?> markerInterface) {
        this.markerInterface = markerInterface;
    }

    public Class<? extends DevBaseMapperFactoryBean> getMapperFactoryBeanClass() {
        return mapperFactoryBeanClass;
    }

    public void setMapperFactoryBeanClass(Class<? extends DevBaseMapperFactoryBean> mapperFactoryBeanClass) {
        this.mapperFactoryBeanClass = mapperFactoryBeanClass;
    }

    public String getDevBaseSqlSessionTemplatesBeanName() {
        return devBaseSqlSessionTemplatesBeanName;
    }

    public void setDevBaseSqlSessionTemplatesBeanName(String devBaseSqlSessionTemplatesBeanName) {
        this.devBaseSqlSessionTemplatesBeanName = devBaseSqlSessionTemplatesBeanName;
    }

    public String getDevBaseSqlInjectorsBeanName() {
        return devBaseSqlInjectorsBeanName;
    }

    public void setDevBaseSqlInjectorsBeanName(String devBaseSqlInjectorsBeanName) {
        this.devBaseSqlInjectorsBeanName = devBaseSqlInjectorsBeanName;
    }

    public String getDevBaseActionManagerBeanName() {
        return devBaseActionManagerBeanName;
    }

    public void setDevBaseActionManagerBeanName(String devBaseActionManagerBeanName) {
        this.devBaseActionManagerBeanName = devBaseActionManagerBeanName;
    }

    public DevBaseActionManager getDevBaseActionManager() {
        return devBaseActionManager;
    }

    public void setDevBaseActionManager(DevBaseActionManager devBaseActionManager) {
        this.devBaseActionManager = devBaseActionManager;
    }

    public String getLazyInitialization() {
        return lazyInitialization;
    }

    public void setLazyInitialization(String lazyInitialization) {
        this.lazyInitialization = lazyInitialization;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public boolean isAddToConfig() {
        return addToConfig;
    }

    public void setAddToConfig(boolean addToConfig) {
        this.addToConfig = addToConfig;
    }

    public DevBaseSqlSessions getDevBaseSqlSessionTemplates() {
        return devBaseSqlSessionTemplates;
    }

    public void setDevBaseSqlSessionTemplates(DevBaseSqlSessions devBaseSqlSessionTemplates) {
        this.devBaseSqlSessionTemplates = devBaseSqlSessionTemplates;
    }

    public DevBaseSqlInjector getDevBaseSqlInjectors() {
        return devBaseSqlInjectors;
    }

    public void setDevBaseSqlInjectors(DevBaseSqlInjector devBaseSqlInjectors) {
        this.devBaseSqlInjectors = devBaseSqlInjectors;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public String getBeanName() {
        return beanName;
    }

    public BeanNameGenerator getNameGenerator() {
        return nameGenerator;
    }

    public void setNameGenerator(BeanNameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.basePackage, "Property 'basePackage' is required");
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if (this.processPropertyPlaceHolders) {
            this.processPropertyPlaceHolders();
        }
        DevBaseClassPathMapperScanner scanner = new DevBaseClassPathMapperScanner(registry);
        scanner.setAddToConfig(addToConfig);
        scanner.setAnnotationClass(this.annotationClass);
        scanner.setMarkerInterface(this.markerInterface);
        scanner.setResourceLoader(this.applicationContext);
        scanner.setBeanNameGenerator(this.nameGenerator);
        scanner.setMapperFactoryBeanClass(this.mapperFactoryBeanClass);

        scanner.setDevBaseActionManagerBeanName(devBaseActionManagerBeanName);
        scanner.setDevBaseSqlSessionTemplatesBeanName(devBaseSqlSessionTemplatesBeanName);
        scanner.setDevBaseSqlInjectorsBeanName(devBaseSqlInjectorsBeanName);

        scanner.setDevBaseSqlSessions(devBaseSqlSessionTemplates);
        scanner.setDevBaseSqlInjector(devBaseSqlInjectors);
        scanner.setDevBaseActionManager(devBaseActionManager);

        if (StringUtils.hasText(lazyInitialization)) {
            scanner.setLazyInitialization(Boolean.parseBoolean(lazyInitialization));
        }
        scanner.registerFilters();
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    private void processPropertyPlaceHolders() {
        Map<String, PropertyResourceConfigurer> prcs = this.applicationContext.getBeansOfType(PropertyResourceConfigurer.class);
        if (!prcs.isEmpty() && this.applicationContext instanceof ConfigurableApplicationContext) {
            BeanDefinition mapperScannerBean = ((ConfigurableApplicationContext) applicationContext).getBeanFactory().getBeanDefinition(beanName);
            DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
            factory.registerBeanDefinition(beanName, mapperScannerBean);

            for (PropertyResourceConfigurer prc : prcs.values()) {
                prc.postProcessBeanFactory(factory);
            }
            PropertyValues values = mapperScannerBean.getPropertyValues();

            this.basePackage = this.updatePropertyValue("basePackage", values);
            this.devBaseSqlSessionTemplatesBeanName = this.updatePropertyValue("devBaseSqlSessionTemplatesBeanName", values);
            this.devBaseSqlInjectorsBeanName = this.updatePropertyValue("devBaseSqlInjectorsBeanName", values);
            this.devBaseActionManagerBeanName = this.updatePropertyValue("devBaseActionManagerBeanName", values);
            this.lazyInitialization = this.updatePropertyValue("lazyInitialization", values);
        }

        this.basePackage = Optional.ofNullable(this.basePackage).map(getEnvironment()::resolvePlaceholders).orElse(null);
        this.devBaseSqlSessionTemplatesBeanName = Optional.ofNullable(this.devBaseSqlSessionTemplatesBeanName).map(getEnvironment()::resolvePlaceholders).orElse(null);
        this.devBaseSqlInjectorsBeanName = Optional.ofNullable(this.devBaseSqlInjectorsBeanName).map(getEnvironment()::resolvePlaceholders).orElse(null);
        this.devBaseActionManagerBeanName = Optional.ofNullable(this.devBaseActionManagerBeanName).map(getEnvironment()::resolvePlaceholders).orElse(null);
        this.lazyInitialization = Optional.ofNullable(this.lazyInitialization).map(getEnvironment()::resolvePlaceholders).orElse(null);
    }

    private String updatePropertyValue(String propertyName, PropertyValues values) {
        PropertyValue property = values.getPropertyValue(propertyName);
        if (property == null) {
            return null;
        } else {
            Object value = property.getValue();
            if (value == null) {
                return null;
            } else if (value instanceof String) {
                return value.toString();
            } else {
                return value instanceof TypedStringValue ? ((TypedStringValue) value).getValue() : null;
            }
        }
    }

    private Environment getEnvironment() {
        return this.applicationContext.getEnvironment();
    }
}
