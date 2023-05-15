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
        InsertTableAction insertAction = new InsertTableAction("bus", "jbxx_bub");
        insertAction.putData("eq_id", "3");
        insertAction.putData("brand", "brand");
        insertAction.putData("check_data", "2023-04-03");
        insertAction.putData("solid", "invalid");
        insertAction.putData("create_time", "2021-01-01 01:00:00");
        insertAction.putData("create_by", 1);
        insertAction.putData("comment", "无");
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
        QueryTableAction queryAction = new QueryTableAction("bus", "jbxx_bub");
        // 范围查询
        queryAction.putComparatorParam("create_time", Comparator.GTE, "2020-01-01 00:00:00");
        queryAction.putComparatorParam("create_time", Comparator.LTE, "2023-12-31 23:59:59");
        // 范围查询，有索引时数值明确时建议使用 IN
        // queryAction.putRangeParam("id", RangeType.IN, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97);
        queryAction.putRangeParam("create_by", RangeType.IN, 1, 50);
        // 简单条件 putParam 或者 putRangeParam 使用 Comparator.EQUALS  以下两种方法等价
        queryAction.putParam("comment", "无");
        queryAction.putComparatorParam("comment", Comparator.EQUALS, "无");
        // 模糊条件
        queryAction.putLikeParam("brand", LikeType.ALL, "brand");
        List<Map<String, Object>> maps = db.doActionGetList(queryAction);

        System.out.println("maps.size = " + maps.size());
    }

    /**
     * 单表删除
     */
    @Test
    public void deleteTest() {
        // 删除 删除api于查询基本一致
        DeleteTableAction deleteAction = new DeleteTableAction("bus", "jbxx_bub");
        // 范围条件 range
        deleteAction.putComparatorParam("create_time", Comparator.GTE, "2021-01-01 00:00:00");
        deleteAction.putComparatorParam("create_time", Comparator.LTE, "2021-12-31 23:59:59");
        // 范围条件，有索引时数值明确时建议使用 IN
        deleteAction.putRangeParam("create_by", RangeType.IN, 1, 2, 3);
        // 简单条件 putParam 或者 putRangeCdn 使用 Comparator.EQUALS  以下两种方法等价
        deleteAction.putParam("eq_id", 3);
        deleteAction.putComparatorParam("eq_id", Comparator.EQUALS, 3);
        // 模糊条件
        deleteAction.putLikeParam("solid", LikeType.ALL, "invalid");
        db.doAction(deleteAction);
    }

    /**
     * 单表修改
     */
    @Test
    public void updateTest() {
        // 修改，条件设置与查询、删除基本一致，设置数据使用putData
        UpdateTableAction updateAction = new UpdateTableAction("bus", "jbxx_bub");
        // 范围条件
        updateAction.putComparatorParam("create_time", Comparator.GTE, "2023-05-31 00:00:00");
        updateAction.putComparatorParam("create_time", Comparator.LTE, "2023-05-31 00:00:00");
        // 范围条件，但 NOT IN 不建议使用哦
        updateAction.putRangeParam("create_by", RangeType.NOT_IN, 1, 50);
        // 简单条件
        updateAction.putParam("battery", "battery");
        updateAction.putComparatorParam("check_uid", Comparator.NOT_EQUALS, 1);
        // 模糊条件
        updateAction.putLikeParam("brand", LikeType.ALL, "update");

        // 设置数据
        updateAction.putData("sdate", "2024-01-01");
        updateAction.putData("check_date", "2024-01-02");
        updateAction.putData("cylin", "cylin");
        updateAction.putData("trach", "trach");
        updateAction.putData("brand", "update2");
        db.doAction(updateAction);
    }

    /**
     * 联表查询或者其他复杂增删改操作 定义在数据库中的mybatis脚本
     */
    @Test
    public void loadTest() {
        LoadAction loadAction = Actions.getLoad("bus", "load_task_all_info");
        loadAction.putParam("taskid", 50);
        Map<String, Object> taskInfo = db.doActionGetOne(loadAction);
        System.out.println("taskInfo = " + taskInfo);
    }

    @Test
    public void transactionTest() {
        commonService.useTransaction();
    }


}
