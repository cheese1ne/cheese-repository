package com.cheese.db.autoconfigure;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 复合注解，目前仅用于DevBaseDB的装配
 * 加入此注解后，默认扫描全部自动装配类，具体配置的开关请参考相关配置类
 *
 * @author sobann
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({DevBaseDBAutoConfiguration.class})
public @interface EnableDevBase {
}
