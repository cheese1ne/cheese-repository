package com.cheese.db.server.expore.http;

import com.cheese.db.common.service.DevBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HTTP服务暴露方式的配置类
 *
 * @author sobann
 */
@Configuration
public class HttpDevBaseResourceAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(HttpDevBaseResourceAutoConfiguration.class);

    @Bean
    public HttpDevBaseResourceController mvcController(DevBaseService devBaseService) {
        logger.info("prepare to initialize HttpDevBaseResourceController, expose the mvc service");
        return new HttpDevBaseResourceController(devBaseService);
    }

}
