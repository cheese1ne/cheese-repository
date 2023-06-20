package com.cheese.db.common.service;

import com.cheese.db.common.condition.Action;
import com.cheese.db.common.condition.page.IPage;
import com.cheese.db.common.wrapper.WrapperResult;

import java.util.List;
import java.util.Map;

/**
 * devbase服务调用接口
 *
 * @author sobann
 */
public interface DevBaseService {

    /**
     * 执行action获取结果,此方法为通用方法,
     * String 为
     * 默认result是map时
     *
     * @param action
     * @return
     */
    <R> WrapperResult<Map<String, R>, R> doAction(Action action);
    String MAP_WRAPPER_ACTION_URL = "/wrapper/map/action";

    /**
     * 查询元素列表
     *
     * @param action
     * @return
     */
    List<Map<String, Object>> doActionGetList(Action action);
    String MAP_LIST_URL = "/map/list";

    /**
     * 查询单个元素
     *
     * @param action
     * @return
     */
    Map<String, Object> doActionGetOne(Action action);
    String MAP_SINGLE_URL = "/map/single";

    /**
     * 查询元素分页
     *
     * @param action
     * @param page
     * @return
     */
    IPage<Map<String, Object>> doActionGetPage(IPage<Map<String, Object>> page, Action action);
    String MAP_PAGE_URL = "/map/page";


//    /**
//     * 查询单个元素
//     *
//     * @param bytes
//     * @return
//     */
//    Map<String, Object> doActionGetOneSerialize(byte[] bytes);
//    String MAP_SERIAL_SINGLE_URL = "/map/serialize/single";
}
