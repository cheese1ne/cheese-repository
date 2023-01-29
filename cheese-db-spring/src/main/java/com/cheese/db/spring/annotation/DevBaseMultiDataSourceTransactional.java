package com.cheese.db.spring.annotation;


import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 多数据源的事务管理
 * 多数据源配置的管理分布式事务管理，利用代码+切面实现
 *
 * @author sobann
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface DevBaseMultiDataSourceTransactional {

    /**
     * 需要进行事务管理的数据库标识
     */
    String[] transactionDbKeys() default {};

    @AliasFor("multiTransactional")
    Transactional[] value() default {};

    @AliasFor("value")
    Transactional[] multiTransactional() default {};

}
