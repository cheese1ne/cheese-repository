package com.cheese.db.server.expore.http;

import com.cheese.db.common.condition.page.IPage;
import com.cheese.db.common.service.DevBaseService;
import com.cheese.db.common.wrapper.ActionResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * http暴露方式
 * servlet返回值包装进行一层包装，可以传输错误信息
 *
 * @author sobann
 */
@RestController
@RequestMapping("/devbase/server")
public class HttpDevBaseResourceController {

    private final DevBaseService devBaseService;

    public HttpDevBaseResourceController(DevBaseService devBaseService) {
        this.devBaseService = devBaseService;
    }

    /**
     * 通用服务调用方式
     *
     * @param action
     * @return
     */
    @PostMapping(DevBaseService.MAP_WRAPPER_ACTION_URL)
    public ActionResult doAction(@RequestBody byte[] action) {
        return devBaseService.doAction(action);
    }

    /**
     * 获取数据列表
     *
     * @param action
     * @return
     */
    @PostMapping(DevBaseService.MAP_LIST_URL)
    public List<Map<String, Object>> doActionGetList(@RequestBody byte[] action) {
        return devBaseService.doActionGetList(action);
    }

    /**
     * 获取单个元素
     *
     * @param action
     * @return
     */
    @PostMapping(DevBaseService.MAP_SINGLE_URL)
    public Map<String, Object> doActionGetOne(@RequestBody byte[] action) {
        return devBaseService.doActionGetOne(action);
    }

    /**
     * 分页查询
     *
     * @param action
     * @return
     */
    @PostMapping(DevBaseService.MAP_PAGE_URL)
    public IPage<Map<String, Object>> doActionGetPage(
            @RequestParam(value = "current", required = false, defaultValue = "1") int current,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestBody byte[] action) {
        return devBaseService.doActionGetPage(current, size, action);
    }

}
