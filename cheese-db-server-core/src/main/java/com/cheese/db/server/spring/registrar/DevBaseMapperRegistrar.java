package com.cheese.db.server.spring.registrar;

import com.cheese.db.server.spring.annotation.DevBaseMappers;
import com.cheese.db.server.spring.bean.DevBaseMapperFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DevBaseMappers注解配置元数据注册器
 * <p>
 * 通过DevBaseMapperScannerConfigurer包扫描器完成
 * devbase 持久层接口的beanDefinition的创建
 *
 * @author sobann
 */
public class DevBaseMapperRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(DevBaseMappers.class.getName()));
        if (mapperScanAttrs != null) {
            this.registerBeanDefinitions(mapperScanAttrs, registry, generateBaseBeanName(annotationMetadata, 0));
        }
    }

    void registerBeanDefinitions(AnnotationAttributes annoAttrs, BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DevBaseMapperScannerConfigurer.class);

        //beanDefinition属性填充
        builder.addPropertyValue("processPropertyPlaceHolders", true);

        Class<? extends Annotation> annotationClass = annoAttrs.getClass("annotationClass");
        if (!Annotation.class.equals(annotationClass)) {
            builder.addPropertyValue("annotationClass", annotationClass);
        }

        Class<?> markerInterface = annoAttrs.getClass("markerInterface");
        if (!Class.class.equals(markerInterface)) {
            builder.addPropertyValue("markerInterface", markerInterface);
        }

        Class<? extends DevBaseMapperFactoryBean> mapperFactoryBeanClass = annoAttrs.getClass("factoryBean");
        if (!DevBaseMapperFactoryBean.class.equals(mapperFactoryBeanClass)) {
            builder.addPropertyValue("mapperFactoryBeanClass", mapperFactoryBeanClass);
        }

        //devSqlSessionTemplatesRef
        String sqlSessionTemplateRef = annoAttrs.getString("devBaseSqlSessionTemplates");
        if (StringUtils.hasText(sqlSessionTemplateRef)) {
            builder.addPropertyValue("devBaseSqlSessionTemplatesBeanName", annoAttrs.getString("devBaseSqlSessionTemplates"));
        }

        //devBaseSqlInjectorsRef
        String devBaseSqlInjectorsRef = annoAttrs.getString("devBaseSqlInjectors");
        if (StringUtils.hasText(devBaseSqlInjectorsRef)) {
            builder.addPropertyValue("devBaseSqlInjectorsBeanName", annoAttrs.getString("devBaseSqlInjectors"));
        }

        //devBaseActionCheckers
        String devBaseActionCheckersRef = annoAttrs.getString("devBaseActionManager");
        if (StringUtils.hasText(devBaseActionCheckersRef)) {
            builder.addPropertyValue("devBaseActionManagerBeanName", annoAttrs.getString("devBaseActionManager"));
        }

        List<String> basePackages = Arrays.stream(annoAttrs.getClassArray("mapperClasses")).map(ClassUtils::getPackageName).collect(Collectors.toList());
        String lazyInitialization = annoAttrs.getString("lazyInitialization");
        if (StringUtils.hasText(lazyInitialization)) {
            builder.addPropertyValue("lazyInitialization", lazyInitialization);
        }

        builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(basePackages));
        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
    }

    private static String generateBaseBeanName(AnnotationMetadata importingClassMetadata, int index) {
        return importingClassMetadata.getClassName() + "#" + DevBaseMapperRegistrar.class.getSimpleName() + "#" + index;
    }
}
