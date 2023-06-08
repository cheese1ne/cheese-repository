package com.cheese.db.spring.annotation;


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
@Target(value = { ElementType.METHOD, ElementType.TYPE})
public @interface DevBaseMultiDataSourceTransactional {

    /**
     * 需要进行事务管理的数据库标识
     */
    String[] transactionDbKeys() default {};

}
