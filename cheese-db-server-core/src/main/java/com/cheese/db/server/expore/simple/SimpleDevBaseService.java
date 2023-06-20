package com.cheese.db.server.expore.simple;

import com.cheese.db.common.condition.Action;
import com.cheese.db.common.condition.page.IPage;
import com.cheese.db.common.service.DevBaseService;
import com.cheese.db.common.wrapper.WrapperResult;
import com.cheese.db.server.core.mapper.DB;

import java.util.List;
import java.util.Map;

/**
 * 默认的服务提供者
 *
 * @author sobann
 */
public class SimpleDevBaseService implements DevBaseService {

    private final DB db;

    public SimpleDevBaseService(DB db) {
        this.db = db;
    }

    @Override
    public <R> WrapperResult<Map<String, R>, R> doAction(Action action) {
        return db.doAction(action);
    }

    @Override
    public List<Map<String, Object>> doActionGetList(Action action) {
        return db.doActionGetList(action);
    }

    @Override
    public Map<String, Object> doActionGetOne(Action action) {
        return db.doActionGetOne(action);
    }

    @Override
    public IPage<Map<String, Object>> doActionGetPage(IPage<Map<String, Object>> page, Action action) {
        return db.doActionGetPage(page, action);
    }

}
