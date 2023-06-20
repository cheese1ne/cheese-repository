package com.cheese.db.rpc.mock;

import com.cheese.db.common.service.DevBaseServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 屏蔽rpc调用，测试时使用
 *
 * @author sobann
 */
@Configuration
public class MockRpcAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(MockRpcAutoConfiguration.class);

    @Bean
    public DevBaseServiceProvider mockShiroServiceProvider(){
        logger.info("prepare to initialize MockDevBaseServiceProvider");
        return new MockDevBaseServiceProvider();
    }
}
