package com.cheese.db.rpc.dubbo;

import com.cheese.db.common.service.DevBaseService;
import com.cheese.db.common.service.DevBaseServiceProvider;
import org.apache.dubbo.config.annotation.Reference;

/**
 * DevBase服务提供者dubbo实现
 *
 * @author sobann
 */
public class DubboDevBaseServiceProvider implements DevBaseServiceProvider {

    @Reference(check = false)
    private DevBaseService devBaseService;

    @Override
    public DevBaseService getDevBaseService() {
        return devBaseService;
    }
}
