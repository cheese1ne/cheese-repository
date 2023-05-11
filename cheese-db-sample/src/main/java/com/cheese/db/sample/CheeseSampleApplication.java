package com.cheese.db.sample;

import com.cheese.db.autoconfigure.EnableDevBase;
import com.cheese.db.core.condition.Action;
import com.cheese.db.core.condition.insert.InsertTableAction;
import com.cheese.db.core.condition.manager.DevBaseActionManager;
import com.cheese.db.core.mapper.DB;
import com.cheese.db.sample.service.ICommonService;
import com.cheese.db.spring.injector.collector.SysSqlConfigInjectMetaCollector;
import com.cheese.db.spring.injector.collector.dialect.MysqlDialectCollector;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

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
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private ICommonService commonService;

    @Override
    public void run(String... args) throws Exception {
//        // 注册行为可以监听ContextRefreshedEvent事件
//        LoadAction action = Actions.getLoad(DevBaseConstant.DEFAULT_SQL_CONFIG_DATASOURCE, DevBaseConstant.DEFAULT_SQL_CONFIG_CODE);
//        List<InjectMeta> injectMetas = db.doActionGetList(action, InjectMeta.class);
//        //默认注入到DB.class中,可根据项目实际持久层mapper实现
//        applicationContext.publishEvent(new SqlInjectEvent(this, DB.class, InjectType.ALL, injectMetas));


//        InsertTableAction insertAction = new InsertTableAction("bus", "tst_one_table");
        InsertTableAction insertAction = new InsertTableAction("bus", "jbxx_bub");
        insertAction.putData("eq_id", "3");
        insertAction.putData("brand", "asdasd");
        insertAction.putData("check_data", "2023-04-03");
        insertAction.putData("solid", "无");
        db.doAction(insertAction);

        Long id = insertAction.getId();
        System.out.println("id = " + id);


//        action = Actions.getLoad(DevBaseConstant.DEFAULT_SQL_CONFIG_DATASOURCE, DevBaseConstant.DEFAULT_TABLE_META_CONFIG_CODE);
//        action.putParam(DevBaseConstant.TABLE_SCHEMA_KEY, "wish-bus");
//
//
//        List<DefaultTableMeta> defaultTableMetas = db.doActionGetList(action, DefaultTableMeta.class);
//        // 需要根据元数据组装表格的增删改查InjectMeta并且注入
//
//        Map<String, List<DefaultTableMeta>> tableMetaListMap = defaultTableMetas.stream().collect(Collectors.groupingBy(DefaultTableMeta::getTableName));
//        for (String tableName : tableMetaListMap.keySet()) {
//            final List<DefaultTableMeta> tableMetaList = tableMetaListMap.get(tableName);
//
//        }

//        action = Actions.getSelect("bus", "cfg_cal_url");
//        List<Map<String, Object>> maps = db.doActionGetList(action);
//        System.out.println("maps = " + maps);

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
                    insertAction.putData("create_time", "2023-05-11 12:30:29");
                    System.out.println("custom devBaseActionManager running");
                }
            }
        };
        return devBaseActionManager;
    }
}
