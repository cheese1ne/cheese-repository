package com.cheese.db.sample;

import com.cheese.db.autoconfigure.EnableDevBase;
import com.cheese.db.core.condition.Action;
import com.cheese.db.core.condition.simple.delete.DeleteTableAction;
import com.cheese.db.core.condition.simple.insert.InsertTableAction;
import com.cheese.db.core.condition.manager.DevBaseActionManager;
import com.cheese.db.core.enums.Comparator;
import com.cheese.db.core.enums.LikeType;
import com.cheese.db.core.enums.RangeType;
import com.cheese.db.core.mapper.DB;
import com.cheese.db.spring.injector.collector.SysSqlConfigInjectMetaCollector;
import com.cheese.db.spring.injector.collector.dialect.MysqlDialectCollector;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.Date;

/**
 * devbase功能可以兼容DataSourceAutoConfiguration的功能，进行相应的配置即可
 *
 * @author sobann
 */
@EnableDevBase
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CheeseSampleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CheeseSampleApplication.class, args);
    }

    @Resource
    private DB db;

    @Override
    public void run(String... args) throws Exception {
        // 新增
        InsertTableAction insertAction = new InsertTableAction("bus", "jbxx_bub");
        insertAction.putData("eq_id", "3");
        insertAction.putData("brand", "brand");
        insertAction.putData("check_data", "2023-04-03");
        insertAction.putData("solid", "无");
        db.doAction(insertAction);
        // 获取自增主键 需要设置数据库表自增，后续可以借助DevBaseActionManager使用雪花id，主键column目前只能精确到数据库配置 devbase-db.configuration.${dbKey}.insert-key-property=id，无法精确配置到表(由于无实体配置)
        Long id = insertAction.getId();
        // 删除
        DeleteTableAction deleteAction = new DeleteTableAction("bus", "jbxx_bub");
        // 范围删除 range
        deleteAction.putComparatorCdn("create_time", Comparator.GTE, "2022-10-11 12:30:29");
        deleteAction.putComparatorCdn("create_time", Comparator.LTE,"2023-05-13 12:30:29");
        // 范围删除，有索引时数值明确时建议使用 IN
        deleteAction.putRangeCdn("id", RangeType.IN,95, 96, 97);
        // 根据字段删除 putCdn 或者 putRangeCdn 使用 Comparator.EQUALS  以下两种方法等价
        deleteAction.putCdn("eq_id", 3);
        deleteAction.putComparatorCdn("eq_id", Comparator.EQUALS,3);
        // 模糊删除
        deleteAction.putLikeCdn("brand", LikeType.ALL, "brand");
        db.doAction(deleteAction);

        //事务测试
//        commonService.useTransaction();
    }

    @Bean
    public SysSqlConfigInjectMetaCollector sysSqlConfigInjectMetaCollector() {
        return new SysSqlConfigInjectMetaCollector();
    }

    @Bean
    public MysqlDialectCollector mysqlDialectCollector() {
        return new MysqlDialectCollector();
    }


    @Bean
    public DevBaseActionManager devBaseActionManager() {
        DevBaseActionManager devBaseActionManager = new DevBaseActionManager() {
            @Override
            public void manager(Action action) {
                if (action instanceof InsertTableAction) {
                    InsertTableAction insertAction = (InsertTableAction) action;
                    insertAction.putData("create_by", 50);
                    insertAction.putData("create_time", new Date());
                    System.out.println("custom devBaseActionManager running");
                }
            }
        };
        return devBaseActionManager;
    }
}
