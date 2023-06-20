package com.cheese.db.server.spring.injector.event;

import com.cheese.db.server.spring.enums.InjectType;
import com.cheese.db.server.spring.injector.metadata.InjectMeta;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * sql注入事件
 *
 * @author sobann
 */
public class SqlInjectEvent extends ApplicationEvent {

    private final Class<?> mapperInterface;

    private final InjectType injectType;

    private final List<InjectMeta> injectMetaList;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public SqlInjectEvent(Object source,Class<?> mapperInterface, InjectType injectType, List<InjectMeta> injectMetaList) {
        super(source);
        this.mapperInterface = mapperInterface;
        this.injectType = injectType;
        this.injectMetaList = injectMetaList;
    }

    public Class<?> getMapperInterface() {
        return mapperInterface;
    }

    public InjectType getInjectType() {
        return injectType;
    }

    public List<InjectMeta> getInjectMetaList() {
        return injectMetaList;
    }
}
