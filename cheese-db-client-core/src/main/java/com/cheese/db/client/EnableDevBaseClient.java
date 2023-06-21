package com.cheese.db.client;

import com.cheese.db.rpc.DevBaseRpcAutoImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 复合注解，devbase服务消费者
 *
 * @author sobann
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({DevBaseRpcAutoImportSelector.class})
public @interface EnableDevBaseClient {
}
