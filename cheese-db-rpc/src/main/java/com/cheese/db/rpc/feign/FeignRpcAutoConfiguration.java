package com.cheese.db.rpc.feign;

import com.cheese.db.common.service.DevBaseServiceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign的rpc调用方式
 *
 * @author sobann
 */
@Configuration
@EnableFeignClients("com.cheese.db.rpc.feign")
public class FeignRpcAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(FeignRpcAutoConfiguration.class);

    @Bean
    public DevBaseFeignHystrix shiroFeignHystrix() {
        logger.info("prepare to initialize DevBaseFeignHystrix");
        return new DevBaseFeignHystrix();
    }

    @Bean
    public DevBaseServiceProvider feiginShiroServiceProvider(){
        logger.info("prepare to initialize FeignDevBaseServiceProvider");
        return new FeignDevBaseServiceProvider();
    }

}
