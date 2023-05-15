package com.cheese.db.sample;

import com.cheese.db.autoconfigure.EnableDevBase;
import com.cheese.db.core.condition.Action;
import com.cheese.db.core.condition.manager.DevBaseActionManager;
import com.cheese.db.core.condition.simple.insert.InsertTableAction;
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
