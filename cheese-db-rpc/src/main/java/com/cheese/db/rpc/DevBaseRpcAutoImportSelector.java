package com.cheese.db.rpc;

import com.cheese.db.rpc.common.enums.RpcEnum;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * rpc调用装配，给服务调用者使用
 *
 * @author sobann
 */
public class DevBaseRpcAutoImportSelector implements ImportSelector, EnvironmentAware {
    private static final Logger logger = LoggerFactory.getLogger(DevBaseRpcAutoImportSelector.class);
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Boolean mockEnable = environment.getProperty("devbase-db.mock.enable", Boolean.class, false);
        if (mockEnable) {
            logger.info("devbase-db.mock.enable is true, prepare to load MockRpcAutoConfiguration");
            return new String[]{RpcEnum.MOCK.getFullClassName()};
        }
        String rpcType = environment.getProperty("devbase-db.rpc.type", "");
        if (!StringUtils.isBlank(rpcType)) {
            String configuration = RpcEnum.parseType(rpcType);
            logger.info("Auto Configuration For DevBaseRpcService is :{}", rpcType);
            return new String[]{configuration};
        }
        //服务调用者本身不需要rpc调用方式获取DevBase服务
        return new String[0];
    }

}
