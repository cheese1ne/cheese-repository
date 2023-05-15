package com.cheese.db.sample.service.impl;

import com.cheese.db.core.condition.simple.insert.InsertTableAction;
import com.cheese.db.core.mapper.DB;
import com.cheese.db.sample.service.ICommonService;
import com.cheese.db.spring.annotation.DevBaseMultiDataSourceTransactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

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

        InsertTableAction action = new InsertTableAction("bus", "bus_cheese_repository_table");
        action.putData("time", LocalDateTime.now());
        action.putData("location", "湖北武汉");
        action.putData("person", "cheese");
        db.doAction(action);

        action = new InsertTableAction("sys", "sys_cheese_repository_table");
        action.putData("time", new Date());
        action.putData("location", "光谷app广场");
        action.putData("person", "sobann");
        db.doAction(action);
        throw new RuntimeException("rollback transaction");
    }
}
