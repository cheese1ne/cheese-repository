package com.cheese.db.rpc.serializer.hessian;

import com.cheese.db.rpc.serializer.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * hessian 序列化自动配置
 *
 * @author sobann
 */
@Configuration
public class HessianSerializeAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(HessianSerializeAutoConfiguration.class);

    /**
     * 默认的序列化类，使用hessian2
     *
     * @return
     */
    @Bean
    public Serializer defaultSerializer() {
        logger.info("prepare to initialize hessianSerializer");
        return new HessianSerializer();
    }

}
