package com.cheese.db.sample.service.impl;

import com.cheese.db.core.condition.Actions;
import com.cheese.db.core.condition.load.LoadAction;
import com.cheese.db.core.mapper.DB;
import com.cheese.db.sample.service.ICommonService;
import com.cheese.db.spring.annotation.DevBaseMultiDataSourceTransactional;
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
        LoadAction action = Actions.getLoad("bus", "SAVE_ONE_TABLE");
        action.putData("code", "20230510");
        action.putData("name", "cheese");
        action.putData("val", "sobann");
        action.putData("remark", "cheese-repository");
        db.doAction(action);
//        action = Actions.getLoad("bus", "inset_into_test");
//        db.doAction(action);
//        throw new RuntimeException("rollback transaction");
    }
}
