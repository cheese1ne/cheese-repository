package com.cheese.db.spring.annotation;

import com.cheese.db.core.mapper.DB;
import com.cheese.db.spring.mapper.DevBaseMapperFactoryBean;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 参考@MapperScanner
 *
 * @author sobann
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({DevBaseMapperRegistrar.class})
@Documented
public @interface DevBaseMappers {

    Class<?>[] mapperClasses() default {DB.class};

    Class<? extends Annotation> annotationClass() default Annotation.class;

    Class<?> markerInterface() default Class.class;

    Class<? extends DevBaseMapperFactoryBean> factoryBean() default DevBaseMapperFactoryBean.class;

    String lazyInitialization() default "";

    String devBaseSqlSessionTemplates() default "";

    String devBaseSqlInjectors() default "";

    String devBaseActionManager() default "";
}
