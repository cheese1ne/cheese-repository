package com.cheese.db.rpc.mock;

import com.cheese.db.common.condition.page.IPage;
import com.cheese.db.common.condition.page.PageFactory;
import com.cheese.db.common.service.DevBaseService;
import com.cheese.db.common.service.DevBaseServiceProvider;
import com.cheese.db.common.wrapper.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DevBase服务提供mock实现
 *
 * @author sobann
 */
public class MockDevBaseServiceProvider implements DevBaseServiceProvider {
    private static final Logger logger = LoggerFactory.getLogger(MockDevBaseServiceProvider.class);

    private DevBaseService devBaseService;

    public MockDevBaseServiceProvider() {
        this.devBaseService = new DevBaseService() {

            @Override
            public ActionResult doAction(byte[] action) {
                logger.error("devBaseService Is Mock, Return Mock Result");
                return new ActionResult();
            }

            @Override
            public List<Map<String, Object>> doActionGetList(byte[] action) {
                logger.error("devBaseService Is Mock, Return Mock Result");
                return new ArrayList<>(8);
            }

            @Override
            public Map<String, Object> doActionGetOne(byte[] action) {
                logger.error("devBaseService Is Mock, Return Mock Result");
                return new HashMap<>(8);
            }

            @Override
            public IPage<Map<String, Object>> doActionGetPage(int current, int page, byte[] action) {
                logger.error("devBaseService Is Mock, Return Mock Result");
                return PageFactory.getPage(current, page);
            }
        };
    }

    @Override
    public DevBaseService getDevBaseService() {
        return devBaseService;
    }
}
