package com.cheese.db.rpc.feign;

import com.cheese.db.common.condition.page.IPage;
import com.cheese.db.common.condition.page.PageFactory;
import com.cheese.db.common.wrapper.ActionResult;
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
            public ActionResult doAction(byte[] action) {
                logger.error("DevBaseFeign Is Unavailable Now, Return Default Fail Result");
                return new ActionResult();
            }

            @Override
            public List<Map<String, Object>> doActionGetList(byte[] action) {
                logger.error("DevBaseFeign Is Unavailable Now, Return Default Fail Result");
                return new ArrayList<>(8);
            }

            @Override
            public Map<String, Object> doActionGetOne(byte[] action) {
                logger.error("DevBaseFeign Is Unavailable Now, Return Default Fail Result");
                return new HashMap<>(8);
            }

            @Override
            public IPage<Map<String, Object>> doActionGetPage(int current, int size, byte[] action) {
                logger.error("DevBaseFeign Is Unavailable Now, Return Default Fail Result");
                return PageFactory.getPage(current, size);
            }
        };
    }


    @Override
    public DevBaseFeign create(Throwable throwable) {
        logger.error("DevBaseFeign Is Error !!! Cause Is {}", throwable.getMessage());
        return this.hystrixService;
    }
}
