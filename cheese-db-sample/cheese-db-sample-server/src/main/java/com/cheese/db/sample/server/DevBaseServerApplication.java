package com.cheese.db.sample.server;

import com.cheese.db.common.condition.Action;
import com.cheese.db.common.condition.manager.DevBaseActionManager;
import com.cheese.db.common.condition.simple.insert.InsertTableAction;
import com.cheese.db.server.EnableDevBaseServer;
import com.cheese.db.server.spring.injector.collector.dialect.MysqlDialectCollector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Date;

/**
 * DevBase服务提供者
 *
 * @author sobann
 */
@EnableDevBaseServer
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DevBaseServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevBaseServerApplication.class, args);
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
