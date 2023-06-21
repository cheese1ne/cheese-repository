package com.cheese.db.rpc;

import com.cheese.db.rpc.common.enums.RpcEnum;
import com.cheese.db.rpc.common.enums.SerializeType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * rpc调用装配，给服务调用者使用
 *
 * @author sobann
 */
public class DevBaseRpcAutoImportSelector implements ImportSelector, EnvironmentAware {
    private static final Logger logger = LoggerFactory.getLogger(DevBaseRpcAutoImportSelector.class);
    private Environment environment;

    public static final String MOCK = "devbase-db.mock.enable";
    public static final String SERIALIZER_TYPE = "devbase-db.rpc.serializer.type";
    public static final String CLIENT_RPC_TYPE = "devbase-db.client.rpc-type";
    /*
        TODO 客户端需要一套服务检查设计，根据
        rpc-type 和 rpc-server 确认具体的rpc服务检查 (例如feign检查就可以利用instances实例注册的情况进行)
     */
    public static final String CLIENT_RPC_SERVER = "devbase-db.client.rpc-server";


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> importConfigurations = new ArrayList<>(8);
        String serializeType = environment.getProperty(SERIALIZER_TYPE, "default");
        String serializeConfig = SerializeType.parseType(serializeType);
        importConfigurations.add(serializeConfig);

        Boolean mockEnable = environment.getProperty(MOCK, Boolean.class, false);
        if (mockEnable) {
            logger.info("devbase-db.mock.enable is true, prepare to load MockRpcAutoConfiguration");
            importConfigurations.add(RpcEnum.MOCK.getFullClassName());
            return importConfigurations.toArray(new String[0]);
        }
        String rpcType = environment.getProperty(CLIENT_RPC_TYPE, "");
        if (!StringUtils.isBlank(rpcType)) {
            String configuration = RpcEnum.parseType(rpcType);
            importConfigurations.add(configuration);
            logger.info("Auto Configuration For DevBaseRpcService is :{}", rpcType);
            return importConfigurations.toArray(new String[0]);
        }
        //服务调用者本身不需要rpc调用方式获取DevBase服务
        return importConfigurations.toArray(new String[0]);
    }

}
