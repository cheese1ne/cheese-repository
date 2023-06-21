package com.cheese.db.server.expore.simple;

import com.cheese.db.common.condition.Action;
import com.cheese.db.common.condition.page.IPage;
import com.cheese.db.common.condition.page.PageFactory;
import com.cheese.db.common.condition.simple.insert.InsertTableAction;
import com.cheese.db.common.service.DevBaseService;
import com.cheese.db.common.wrapper.ActionResult;
import com.cheese.db.common.wrapper.MapWrapperResult;
import com.cheese.db.rpc.serializer.Serializer;
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
    private final Serializer serializer;

    public SimpleDevBaseService(DB db, Serializer serializer) {
        this.db = db;
        this.serializer = serializer;
    }

    @Override
    public ActionResult doAction(byte[] action) {
        Action deserialize = serializer.deserialize(action, Action.class);
        MapWrapperResult<Object> wrapper = (MapWrapperResult)db.doAction(deserialize);

        ActionResult actionResult = new ActionResult();
        // 单表插入时返回插入的id
        if (deserialize instanceof InsertTableAction) {
            InsertTableAction insertTableAction = (InsertTableAction) deserialize;
            actionResult.setResult(insertTableAction.getId());
        } else {
            actionResult.setResult(wrapper.getResult());
        }
        return actionResult;
    }

    @Override
    public List<Map<String, Object>> doActionGetList(byte[] action) {
        Action deserialize = serializer.deserialize(action, Action.class);
        return db.doActionGetList(deserialize);
    }

    @Override
    public Map<String, Object> doActionGetOne(byte[] action) {
        Action deserialize = serializer.deserialize(action, Action.class);
        return db.doActionGetOne(deserialize);
    }

    @Override
    public IPage<Map<String, Object>> doActionGetPage(int current, int size, byte[] action) {
        Action deserialize = serializer.deserialize(action, Action.class);
        return db.doActionGetPage(PageFactory.getPage(current, size), deserialize);
    }

}
