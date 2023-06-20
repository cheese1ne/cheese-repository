package com.cheese.db.server.spring.registrar;

import com.cheese.db.common.condition.manager.DevBaseActionManager;
import com.cheese.db.server.spring.bean.DevBaseMapperFactoryBean;
import com.cheese.db.server.spring.injector.DevBaseSqlInjector;
import com.cheese.db.server.spring.wrapper.DevBaseSqlSessions;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * devbase包扫描器
 * 完成持久层接口的beanDefinition的创建
 *
 * @author sobann
 */
public class DevBaseClassPathMapperScanner extends ClassPathBeanDefinitionScanner {

    private boolean addToConfig = true;

    private boolean lazyInitialization;

    private DevBaseSqlSessions devBaseSqlSessions;

    private DevBaseActionManager devBaseActionManager;

    private DevBaseSqlInjector devBaseSqlInjector;

    private String devBaseSqlSessionTemplatesBeanName;
    private String devBaseSqlInjectorsBeanName;
    private String devBaseActionManagerBeanName;

    private Class<? extends Annotation> annotationClass;

    private Class<?> markerInterface;

    private Class<? extends DevBaseMapperFactoryBean> mapperFactoryBeanClass = DevBaseMapperFactoryBean.class;

    public DevBaseClassPathMapperScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public boolean isAddToConfig() {
        return addToConfig;
    }

    public void setAddToConfig(boolean addToConfig) {
        this.addToConfig = addToConfig;
    }

    public boolean isLazyInitialization() {
        return lazyInitialization;
    }

    public void setLazyInitialization(boolean lazyInitialization) {
        this.lazyInitialization = lazyInitialization;
    }

    public DevBaseSqlSessions getDevBaseSqlSessions() {
        return devBaseSqlSessions;
    }

    public void setDevBaseSqlSessions(DevBaseSqlSessions devBaseSqlSessions) {
        this.devBaseSqlSessions = devBaseSqlSessions;
    }

    public DevBaseActionManager getDevBaseActionManager() {
        return devBaseActionManager;
    }

    public void setDevBaseActionManager(DevBaseActionManager devBaseActionManager) {
        this.devBaseActionManager = devBaseActionManager;
    }

    public String getDevBaseActionManagerBeanName() {
        return devBaseActionManagerBeanName;
    }

    public void setDevBaseActionManagerBeanName(String devBaseActionManagerBeanName) {
        this.devBaseActionManagerBeanName = devBaseActionManagerBeanName;
    }

    public DevBaseSqlInjector getDevBaseSqlInjector() {
        return devBaseSqlInjector;
    }

    public void setDevBaseSqlInjector(DevBaseSqlInjector devBaseSqlInjector) {
        this.devBaseSqlInjector = devBaseSqlInjector;
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
        this.mapperFactoryBeanClass = mapperFactoryBeanClass == null ? DevBaseMapperFactoryBean.class : mapperFactoryBeanClass;
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            System.out.println("beanDefinitions is empty");
        } else {
            processBeanDefinitions(beanDefinitions);
        }
        return beanDefinitions;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    public void registerFilters() {
        boolean acceptAllInterfaces = true;

        // if specified, use the given annotation and / or marker interface
        if (this.annotationClass != null) {
            addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
            acceptAllInterfaces = false;
        }

        // override AssignableTypeFilter to ignore matches on the actual marker interface
        if (this.markerInterface != null) {
            addIncludeFilter(new AssignableTypeFilter(this.markerInterface) {
                @Override
                protected boolean matchClassName(String className) {
                    return false;
                }
            });
            acceptAllInterfaces = false;
        }

        if (acceptAllInterfaces) {
            // default include filter that accepts all classes
            addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        }

        // exclude package-info.java
        addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();

            // the mapper interface is the original class of the bean
            // but, the actual class of the bean is MapperFactoryBean
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName); // issue #59
            definition.setBeanClass(this.mapperFactoryBeanClass);

            definition.getPropertyValues().add("addToConfig", this.addToConfig);
            /*
                private DevBaseSqlSessions devBaseSqlSessionTemplates;
                private DevBaseActionCheckers devBaseActionCheckers;
                private DevBaseSqlInjector devBaseSqlInjector;
             */

            boolean explicitFactoryUsed = false;
            if (StringUtils.hasText(this.devBaseSqlSessionTemplatesBeanName)) {
                definition.getPropertyValues().add("devBaseSqlSessionTemplates",
                        new RuntimeBeanReference(this.devBaseSqlSessionTemplatesBeanName));
                explicitFactoryUsed = true;
            }


            if (StringUtils.hasText(this.devBaseSqlInjectorsBeanName)) {
                definition.getPropertyValues().add("devBaseSqlInjector", new RuntimeBeanReference(this.devBaseSqlInjectorsBeanName));
                explicitFactoryUsed = true;
            }

            if (StringUtils.hasText(this.devBaseActionManagerBeanName)) {
                definition.getPropertyValues().add("devBaseActionManager", new RuntimeBeanReference(this.devBaseActionManagerBeanName));
                explicitFactoryUsed = true;
            }

            if (!explicitFactoryUsed) {
                definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
            }
            definition.setLazyInit(lazyInitialization);
        }
    }
}
