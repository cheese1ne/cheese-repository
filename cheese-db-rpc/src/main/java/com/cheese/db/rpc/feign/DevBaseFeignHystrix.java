package com.cheese.db.rpc.feign;

import com.cheese.db.common.condition.Action;
import com.cheese.db.common.condition.page.IPage;
import com.cheese.db.common.condition.page.PageFactory;
import com.cheese.db.common.wrapper.MapWrapperResult;
import com.cheese.db.common.wrapper.WrapperResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 断路器
 *
 * @author sobann
 */
public class DevBaseFeignHystrix implements FallbackFactory<DevBaseFeign> {
    private static final Logger logger = LoggerFactory.getLogger(DevBaseFeignHystrix.class);

    private DevBaseFeign hystrixService;

    public DevBaseFeignHystrix() {
        this.hystrixService = new DevBaseFeign() {
            @Override
            public <R> WrapperResult<Map<String, R>, R> doAction(Action action) {
                logger.error("DevBaseFeign Is Unavailable Now, Return Default Fail Result");
                return MapWrapperResult.Builder.build();
            }

            @Override
            public List<Map<String, Object>> doActionGetList(Action action) {
                logger.error("DevBaseFeign Is Unavailable Now, Return Default Fail Result");
                return new ArrayList<>(8);
            }

            @Override
            public Map<String, Object> doActionGetOne(Action action) {
                logger.error("DevBaseFeign Is Unavailable Now, Return Default Fail Result");
                return new HashMap<>(8);
            }

            @Override
            public IPage<Map<String, Object>> doActionGetPage(int current, int size, Action action) {
                logger.error("DevBaseFeign Is Unavailable Now, Return Default Fail Result");
                return this.doActionGetPage(PageFactory.getPage(current, size), action);
            }

            @Override
            public IPage<Map<String, Object>> doActionGetPage(IPage<Map<String, Object>> page, Action action) {
                logger.error("DevBaseFeign Is Unavailable Now, Return Default Fail Result");
                return page;
            }
        };
    }


    @Override
    public DevBaseFeign create(Throwable throwable) {
        logger.error("DevBaseFeign Is Error !!! Cause Is {}", throwable.getMessage());
        return this.hystrixService;
    }
}
