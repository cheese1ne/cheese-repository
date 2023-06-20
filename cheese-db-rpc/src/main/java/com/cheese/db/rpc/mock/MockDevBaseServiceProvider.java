package com.cheese.db.rpc.mock;

import com.cheese.db.common.condition.Action;
import com.cheese.db.common.condition.page.IPage;
import com.cheese.db.common.service.DevBaseService;
import com.cheese.db.common.service.DevBaseServiceProvider;
import com.cheese.db.common.wrapper.MapWrapperResult;
import com.cheese.db.common.wrapper.WrapperResult;
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
            public <R> WrapperResult<Map<String, R>, R> doAction(Action action) {
                logger.error("devBaseService Is Mock, Return Mock Result");
                return MapWrapperResult.Builder.build();
            }

            @Override
            public List<Map<String, Object>> doActionGetList(Action action) {
                logger.error("devBaseService Is Mock, Return Mock Result");
                return new ArrayList<>(8);
            }

            @Override
            public Map<String, Object> doActionGetOne(Action action) {
                logger.error("devBaseService Is Mock, Return Mock Result");
                return new HashMap<>(8);
            }

            @Override
            public IPage<Map<String, Object>> doActionGetPage(IPage<Map<String, Object>> page, Action action) {
                logger.error("devBaseService Is Mock, Return Mock Result");
                return page;
            }
        };
    }

    @Override
    public DevBaseService getDevBaseService() {
        return devBaseService;
    }
}
