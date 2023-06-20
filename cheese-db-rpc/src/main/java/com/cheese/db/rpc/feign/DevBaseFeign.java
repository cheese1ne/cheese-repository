package com.cheese.db.rpc.feign;

import com.cheese.db.common.condition.Action;
import com.cheese.db.common.condition.page.IPage;
import com.cheese.db.common.service.DevBaseService;
import com.cheese.db.common.wrapper.WrapperResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * feign服务调用
 *
 * @author sobann
 */
@FeignClient(value = "${devbase-db.rpc.server}" ,contextId = "devBaseServerFeign",path = "devbase/server",fallbackFactory = DevBaseFeignHystrix.class)
public interface DevBaseFeign extends DevBaseService {
    /**
     * 执行action获取结果,此方法为通用方法,
     * String 为
     * 默认result是map时
     *
     * @param action
     * @return
     */
    @Override
    @RequestMapping(value = DevBaseService.MAP_WRAPPER_ACTION_URL, method = RequestMethod.POST)
    <R> WrapperResult<Map<String, R>, R> doAction(@RequestBody Action action);

    /**
     * 查询元素列表
     *
     * @param action
     * @return
     */
    @Override
    @RequestMapping(value = DevBaseService.MAP_LIST_URL, method = RequestMethod.POST)
    List<Map<String, Object>> doActionGetList(@RequestBody Action action);

    /**
     * 查询单个元素
     *
     * @param action
     * @return
     */
    @Override
    @RequestMapping(value = DevBaseService.MAP_SINGLE_URL, method = RequestMethod.POST)
    Map<String, Object> doActionGetOne(@RequestBody Action action);

    /**
     * 查询元素分页
     *
     * @param action
     * @param current
     * @param size
     * @return
     */
    @RequestMapping(value = DevBaseService.MAP_PAGE_URL, method = RequestMethod.POST)
    IPage<Map<String, Object>> doActionGetPage(
            @RequestParam(value = "current", required = false, defaultValue = "1") int current,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestBody Action action);

    /**
     * 查询元素分页
     *
     * @param action
     * @param page
     * @return
     */
    @Override
    @RequestMapping(value = DevBaseService.MAP_PAGE_URL, method = RequestMethod.POST)
    IPage<Map<String, Object>> doActionGetPage(@RequestParam("page") IPage<Map<String, Object>> page, @RequestBody Action action);
}
