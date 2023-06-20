package com.cheese.db.server.spring.injector.event;

import com.cheese.db.server.core.mapper.DB;
import com.cheese.db.server.spring.enums.InjectType;
import com.cheese.db.server.spring.injector.collector.InjectMetaCollector;
import com.cheese.db.server.spring.injector.event.runtime.DefaultEventSqlInjector;
import com.cheese.db.server.spring.injector.metadata.InjectMeta;
import com.cheese.db.server.spring.support.DevBaseApplicationContextSupport;
import com.cheese.db.server.spring.wrapper.DevBaseSqlSessions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.SmartApplicationListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * sql注入监听器
 *
 * @author sobann
 */
public class DevBaseSqlInjectListener extends DevBaseApplicationContextSupport implements SmartApplicationListener {

    private static Logger logger = LoggerFactory.getLogger(DevBaseSqlInjectListener.class);
    private DevBaseSqlSessions sqlSessions;


    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType.isAssignableFrom(ContextRefreshedEvent.class) || eventType.isAssignableFrom(SqlInjectEvent.class);
    }


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            onApplicationContextRefreshedEvent((ContextRefreshedEvent) event);
        }

        if (event instanceof SqlInjectEvent) {
            onApplicationSqlInjectEvent((SqlInjectEvent) event);
        }


    }

    /**
     * 容器刷新事件
     *
     * @param event
     */
    private void onApplicationContextRefreshedEvent(ContextRefreshedEvent event) {
        List<InjectMeta> injectMetaList = new ArrayList<>();
        final Map<String, InjectMetaCollector> injectMetaCollectors = DevBaseApplicationContextSupport.getBeans(InjectMetaCollector.class);
        for (InjectMetaCollector collector : injectMetaCollectors.values()) {
            final List<InjectMeta> injectMetas = collector.getInjectMetas();
            injectMetaList.addAll(injectMetas);
        }
        //默认注入到DB.class中,可根据项目实际持久层mapper实现
        DevBaseApplicationContextSupport.getApplicationContext().publishEvent(new SqlInjectEvent(this, DB.class, InjectType.ALL, injectMetaList));
    }


    /**
     * sql注入事件
     *
     * @param event
     */
    private void onApplicationSqlInjectEvent(SqlInjectEvent event) {
        if (sqlSessions == null) {
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
