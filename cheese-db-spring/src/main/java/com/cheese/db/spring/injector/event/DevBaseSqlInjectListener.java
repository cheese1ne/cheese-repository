package com.cheese.db.spring.injector.event;

import com.cheese.db.spring.injector.event.runtime.DefaultEventSqlInjector;
import com.cheese.db.spring.support.DevBaseApplicationContextSupport;
import com.cheese.db.spring.wrappers.DevBaseSqlSessions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

/**
 * sql注入监听器
 *
 * @author sobann
 */
public class DevBaseSqlInjectListener extends DevBaseApplicationContextSupport implements ApplicationListener<SqlInjectEvent> {

    private static Logger logger = LoggerFactory.getLogger(DevBaseSqlInjectListener.class);
    private DevBaseSqlSessions sqlSessions;


    @Override
    public void onApplicationEvent(SqlInjectEvent event) {
        if (sqlSessions == null){
            sqlSessions = DevBaseApplicationContextSupport.getBean(DevBaseSqlSessions.class);
        }
        logger.info("the sqlInjectEvent was monitored, ready to start sql injection");
        try {
            Class<?> mapperInterface = event.getMapperInterface();
            DefaultEventSqlInjector eventSqlInjector = new DefaultEventSqlInjector.Builder(event.getInjectType(),
                    event.getInjectMetaList()).build();
            eventSqlInjector.inspectInject(sqlSessions, mapperInterface);
            logger.info("the sqlInjectEvent was completed");
        } catch (Exception e) {
            logger.error("the sqlInjectEvent was failed, the reason is {}", e.getMessage());
            e.printStackTrace();
        }
    }

}
