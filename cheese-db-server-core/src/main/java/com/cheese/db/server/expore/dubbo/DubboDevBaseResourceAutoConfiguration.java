package com.cheese.db.server.expore.dubbo;

import com.cheese.db.common.service.DevBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Dubbo服务暴露方式的配置类
 *
 * @author sobann
 */
@Configuration
public class DubboDevBaseResourceAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DubboDevBaseResourceAutoConfiguration.class);

    @Bean
    public DubboDevBaseResourceService dubboService(DevBaseService devBaseService) {
        logger.info("prepare to initialize DubboDevBaseResourceService, expose the dubbo service");
        return new DubboDevBaseResourceService(devBaseService);
    }

}
