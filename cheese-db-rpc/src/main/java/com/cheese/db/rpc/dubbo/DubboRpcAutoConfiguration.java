package com.cheese.db.rpc.dubbo;

import com.cheese.db.common.service.DevBaseServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * dubbo的rpc调用方式
 *
 * @author sobann
 */
@Configuration
public class DubboRpcAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DubboRpcAutoConfiguration.class);

    @Bean
    public DevBaseServiceProvider shiroServiceProvider(){
        logger.info("prepare to initialize DubboDevBaseServiceProvider");
        return new DubboDevBaseServiceProvider();
    }
}
