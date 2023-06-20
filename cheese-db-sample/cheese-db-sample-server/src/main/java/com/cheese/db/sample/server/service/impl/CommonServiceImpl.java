package com.cheese.db.sample.server.service.impl;

import com.cheese.db.common.condition.Actions;
import com.cheese.db.common.condition.load.LoadAction;
import com.cheese.db.sample.server.service.ICommonService;
import com.cheese.db.server.core.mapper.DB;
import com.cheese.db.server.spring.annotation.DevBaseMultiDataSourceTransactional;
import org.springframework.stereotype.Service;

/**
 * 测试业务
 *
 * @author sobann
 */
@Service("commonService")
public class CommonServiceImpl implements ICommonService {

    private final DB db;

    public CommonServiceImpl(DB db){
        this.db = db;
    }

    @DevBaseMultiDataSourceTransactional(transactionDbKeys = {"sys", "bus"})
    @Override
    public void useTransaction() {
        LoadAction action = Actions.getLoad("sys", "INSERT_NEW_SYS_USER");
        action.putData("name", "cheese679");
        action.putData("full_name", "cheese-repository");
        action.putData("nick_name", "cheese1ne");
        action.putData("email", "cheese@163.com");
        action.putData("telephone", "13377778888");
        action.putData("password", "cheese1'spassword");
        action.putData("status", 1);
        action.putData("create_by", 50);
        action.putData("remark", "无");
        db.doAction(action);

        action = Actions.getLoad("bus", "UPDATE_BORROW_APPLY_DATA");
        action.putParam("id", 1);
        action.putData("status", 1);
        db.doAction(action);
        throw new RuntimeException("rollback transaction");
    }
}
