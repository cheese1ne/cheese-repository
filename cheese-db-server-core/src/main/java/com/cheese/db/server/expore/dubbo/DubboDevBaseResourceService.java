package com.cheese.db.server.expore.dubbo;

import com.cheese.db.common.condition.page.IPage;
import com.cheese.db.common.service.DevBaseService;
import com.cheese.db.common.wrapper.ActionResult;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;
import java.util.Map;

/**
 * dubbo暴露方式
 *
 * @author sobann
 */
@Service(interfaceClass = DevBaseService.class)
public class DubboDevBaseResourceService implements DevBaseService {

    private final DevBaseService devBaseService;

    public DubboDevBaseResourceService(DevBaseService devBaseService) {
        this.devBaseService = devBaseService;
    }

    @Override
    public ActionResult doAction(byte[] action) {
        return devBaseService.doAction(action);
    }

    @Override
    public List<Map<String, Object>> doActionGetList(byte[] action) {
        return devBaseService.doActionGetList(action);
    }


    @Override
    public Map<String, Object> doActionGetOne(byte[] action) {
        return devBaseService.doActionGetOne(action);
    }

    @Override
    public IPage<Map<String, Object>> doActionGetPage(int current, int size, byte[] action) {
        return devBaseService.doActionGetPage(current, size, action);
    }
}
