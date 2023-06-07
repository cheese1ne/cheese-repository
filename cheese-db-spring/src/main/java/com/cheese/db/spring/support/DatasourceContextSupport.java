package com.cheese.db.spring.support;

import com.cheese.db.spring.datasource.DatasourceContext;

/**
 * 上下文提供者
 *
 * @author sobann
 */
public class DatasourceContextSupport {

    private static ThreadLocal<DatasourceContext> DATASOURCE_CONTEXT = ThreadLocal.withInitial(DatasourceContext::new);

    /**
     * 提供数据源上下文
     *
     * @return
     */
    public static DatasourceContext support() {
        return DATASOURCE_CONTEXT.get();
    }

    /**
     * 设置数据源上下文
     *
     * @param datasourceContext
     */
    public static void set(DatasourceContext datasourceContext) {
        DATASOURCE_CONTEXT.set(datasourceContext);
    }

    /**
     * 清空当前线程的上下文数据
     */
    public static void clear() {
        DATASOURCE_CONTEXT.remove();
    }
}
