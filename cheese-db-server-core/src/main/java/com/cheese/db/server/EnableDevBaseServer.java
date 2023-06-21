package com.cheese.db.server;

import com.cheese.db.rpc.DevBaseRpcAutoImportSelector;
import com.cheese.db.server.spring.autoconfigure.DevBaseDBAutoConfiguration;
import com.cheese.db.server.spring.autoconfigure.DevBaseDbAutoImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 复合注解，devbase服务提供者
 *
 * @author sobann
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({DevBaseDBAutoConfiguration.class, DevBaseDbAutoImportSelector.class, DevBaseRpcAutoImportSelector.class})
public @interface EnableDevBaseServer {
}
