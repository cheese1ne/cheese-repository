package com.cheese.db.spring.injector.collector;

import com.cheese.db.spring.injector.metadata.InjectMeta;

import java.util.List;

/**
 * injectMeta收集者
 *
 * @author sobann
 */
public interface InjectMetaCollector {

    /**
     * 收集注入sql的元数据
     *
     * @return
     */
    List<InjectMeta> getInjectMetas();
}
