package com.cheese.db.rpc.feign;

import com.cheese.db.common.service.DevBaseService;
import com.cheese.db.common.service.DevBaseServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * DevBase服务提供者的feign实现
 *
 * @author sobann
 */
public class FeignDevBaseServiceProvider implements DevBaseServiceProvider {

    @Lazy
    @Autowired
    private DevBaseService devBaseService;

    @Override
    public DevBaseService getDevBaseService() {
        return devBaseService;
    }
}
