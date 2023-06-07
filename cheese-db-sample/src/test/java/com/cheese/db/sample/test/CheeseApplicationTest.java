package com.cheese.db.sample.test;

import com.cheese.db.core.condition.Actions;
import com.cheese.db.core.condition.load.LoadAction;
import com.cheese.db.core.condition.simple.delete.DeleteTableAction;
import com.cheese.db.core.condition.simple.insert.InsertTableAction;
import com.cheese.db.core.condition.simple.query.QueryTableAction;
import com.cheese.db.core.condition.simple.update.UpdateTableAction;
import com.cheese.db.core.enums.Comparator;
import com.cheese.db.core.enums.LikeType;
import com.cheese.db.core.enums.RangeType;
import com.cheese.db.core.mapper.DB;
import com.cheese.db.sample.service.ICommonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * cheese action简单的单元测试
 *
 * @author sobann
 */
@Profile("test")
@SpringBootTest
@RunWith(SpringRunner.class)
public class CheeseApplicationTest {


    @Resource
    private DB db;
    @Resource
    private ICommonService commonService;

    /**
     * 单表新增
     */
    @Test
    public void insertTest() {
        // 新增
        InsertTableAction insertAction = new InsertTableAction("bus", "dt_borrow_manage_borrow_apply");
        insertAction.putData("type", "offline");
        insertAction.putData("apply_code", "TOF-20230103-010");
        insertAction.putData("geodata_id", "10010");
        insertAction.putData("apply_user", "50");
        insertAction.putData("apply_reason", "这是什么 申请一下");
        insertAction.putData("status", 0);
        insertAction.putData("is_deleted", 0);
        insertAction.putData("remark", "无");
        db.doAction(insertAction);
        // 获取自增主键 需要设置数据库表自增，后续可以借助DevBaseActionManager使用雪花id，主键column目前只能精确到数据库配置 devbase-db.configuration.${dbKey}.insert-key-property=id，无法精确配置到表(由于无实体配置)
        Long id = insertAction.getId();
        System.out.println("id = " + id);
    }

    /**
     * 单表查询
     */
    @Test
    public void selectTest() {
        QueryTableAction queryAction = new QueryTableAction("bus", "dt_borrow_manage_borrow_apply");
        // 范围查询
        queryAction.putComparatorParam("create_time", Comparator.GTE, "2020-01-01 00:00:00");
        queryAction.putComparatorParam("create_time", Comparator.LTE, "2023-12-31 23:59:59");
        // 范围查询，有索引时数值明确时建议使用 IN
        // queryAction.putRangeParam("id", RangeType.IN, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97);
        queryAction.putRangeParam("apply_user", RangeType.IN, 1, 50, 51);
        // 简单条件 putParam 或者 putRangeParam 使用 Comparator.EQUALS  以下两种方法等价
        queryAction.putParam("remark", "无");
        queryAction.putComparatorParam("remark", Comparator.EQUALS, "无");
        // 模糊条件
        queryAction.putLikeParam("apply_code", LikeType.ALL, "TOF");
        List<Map<String, Object>> maps = db.doActionGetList(queryAction);

        System.out.println("maps.size = " + maps.size());
    }

    /**
     * 单表删除
     */
    @Test
    public void deleteTest() {
        // 删除 删除api于查询基本一致
        DeleteTableAction deleteAction = new DeleteTableAction("bus", "dt_borrow_manage_borrow_apply");
        // 范围条件 range
        deleteAction.putComparatorParam("create_time", Comparator.GTE, "2021-01-01 00:00:00");
        deleteAction.putComparatorParam("create_time", Comparator.LTE, "2023-12-31 23:59:59");
        // 范围条件，有索引时数值明确时建议使用 IN
        deleteAction.putRangeParam("id", RangeType.IN, Stream.iterate(40, item-> item + 1).limit(60).toArray());
        // 简单条件 putParam 或者 putRangeCdn 使用 Comparator.EQUALS  以下两种方法等价
        deleteAction.putParam("is_deleted", 1);
        deleteAction.putComparatorParam("is_deleted", Comparator.EQUALS, 1);
        // 模糊条件
        deleteAction.putLikeParam("apply_code", LikeType.ALL, "TOF");
        db.doAction(deleteAction);
    }

    /**
     * 单表修改
     */
    @Test
    public void updateTest() {
        // 修改，条件设置与查询、删除基本一致，设置数据使用putData
        UpdateTableAction updateAction = new UpdateTableAction("bus", "dt_borrow_manage_borrow_apply");
        // 范围条件
        updateAction.putComparatorParam("create_time", Comparator.GTE, "2021-01-01 00:00:00");
        updateAction.putComparatorParam("create_time", Comparator.LTE, "2023-12-31 23:59:59");
        // 范围条件，但 NOT IN 不建议使用哦
        updateAction.putRangeParam("id", RangeType.NOT_IN, 1, 2, 3);
        // 简单条件
        updateAction.putParam("is_deleted", 0);
        updateAction.putComparatorParam("status", Comparator.NOT_EQUALS, 1);
        // 模糊条件
        updateAction.putLikeParam("apply_code", LikeType.ALL, "TOF");

        // 设置数据
        updateAction.putData("is_deleted", 1);
        updateAction.putData("apply_reason", "是资料啊 那就申请吧");
        db.doAction(updateAction);
    }

    /**
     * 联表查询或者其他复杂增删改操作 定义在数据库中的mybatis脚本
     */
    @Test
    public void loadTest() {
        LoadAction loadAction = Actions.getLoad("bus", "LOAD_BORROW_APPLY_INFO");
        loadAction.putParam("apply_id", 1);
        loadAction.putParam("is_deleted", 0);
        Map<String, Object> taskInfo = db.doActionGetOne(loadAction);
        System.out.println("applyInfo = " + taskInfo);
    }

    @Test
    public void transactionTest() {
        commonService.useTransaction();
    }


}
