package com.cheese.db.server.spring.support;

import com.cheese.db.common.condition.manager.DevBaseActionManager;
import com.cheese.db.server.spring.injector.DevBaseSqlInjector;
import com.cheese.db.server.spring.wrapper.DevBaseSqlSessions;
import org.springframework.dao.support.DaoSupport;
import org.springframework.util.Assert;

/**
 * 多数据源相关属性提供者
 *
 * @author sobann
 */
public abstract class DevBaseSqlSessionDaoSupport extends DaoSupport {

    private DevBaseSqlSessions devBaseSqlSessionTemplates;
    private DevBaseActionManager devBaseActionManager;
    private DevBaseSqlInjector devBaseSqlInjector;

    public DevBaseSqlSessions getDevBaseSqlSessionTemplates() {
        return devBaseSqlSessionTemplates;
    }

    public void setDevBaseSqlSessionTemplates(DevBaseSqlSessions devBaseSqlSessionTemplates) {
        this.devBaseSqlSessionTemplates = devBaseSqlSessionTemplates;
    }

    public DevBaseActionManager getDevBaseActionManager() {
        return devBaseActionManager;
    }

    public void setDevBaseActionManager(DevBaseActionManager devBaseActionManager) {
        this.devBaseActionManager = devBaseActionManager;
    }

    public DevBaseSqlInjector getDevBaseSqlInjector() {
        return devBaseSqlInjector;
    }

    public void setDevBaseSqlInjector(DevBaseSqlInjector devBaseSqlInjector) {
        this.devBaseSqlInjector = devBaseSqlInjector;
    }

    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {
        Assert.notNull(this.devBaseSqlSessionTemplates, "Property 'sqlSessionTemplates' are required");
    }
}
