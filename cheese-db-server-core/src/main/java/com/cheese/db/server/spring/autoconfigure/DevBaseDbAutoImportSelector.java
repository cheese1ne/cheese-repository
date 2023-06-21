package com.cheese.db.server.spring.autoconfigure;

import com.cheese.db.server.spring.enums.ExposerEnum;
import com.cheese.db.server.spring.enums.TransactionEnum;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * 这里配置一些组件中提供选择的bean实例
 * 例如事务 aspectj 和 aop 二选一
 * 服务暴露方式 http dubbo
 *
 * @author sobann
 */
public class DevBaseDbAutoImportSelector implements ImportSelector, EnvironmentAware {

    public static final String TRANSACTION = "devbase-db.server.transaction-type";
    public static final String EXPOSER = "devbase-db.server.exposer-type";

    private Environment environment;

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        List<String> configurations = new ArrayList<>();
        String transactionProps = environment.getProperty(TRANSACTION, "default");
        String transactionType = TransactionEnum.parseType(transactionProps);
        String exposeProps = environment.getProperty(EXPOSER, "default");
        String exposeType = ExposerEnum.parseType(exposeProps);
        configurations.add(transactionType);
        configurations.add(exposeType);
        return configurations.toArray(new String[0]);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
