package com.cheese.db.spring.injector.simple;

import com.cheese.db.core.support.DevBaseConstant;
import com.cheese.db.spring.injector.DevBaseSqlInjector;
import com.cheese.db.spring.injector.DevBaseSqlInjectorProvider;
import com.cheese.db.spring.injector.DevBaseStatementFactory;
import com.cheese.db.spring.injector.metadata.simple.DefaultInjectMeta;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.util.Map;

/**
 * 默认的sqlInjector提供者
 * 1.项目启动时
 * a.注册sys_config_sql等数据的查询功能
 * b.todo 基础增上改查扩展，增加表的增删改元数据配置表 (后续完成)
 *
 * @author sobann
 */
public class DefaultDevBaseSqlInjectorProvider implements DevBaseSqlInjectorProvider, DevBaseConstant {

    private static DefaultInjectMeta injectMeta = new DefaultInjectMeta();

    static {
        injectMeta.setCode(DEFAULT_SQL_CONFIG_CODE);
        injectMeta.setSqlCommandType(SqlCommandType.SELECT);
        injectMeta.setReturnType(DefaultInjectMeta.class);
        injectMeta.setDbKey(DEFAULT_SQL_CONFIG_DATASOURCE);
        injectMeta.setContent(DEFAULT_SYS_CONFIG_SQL);
    }

    @Override
    public DevBaseSqlInjector getDevBaseSqlInjector() {
        //默认注册的sql目前仅有sys_config_sql的内容
        return (devBaseSqlSessions, type) -> {
            Map<String, SqlSession> sqlSessions = devBaseSqlSessions.getSqlSessions();
            for (Map.Entry<String, SqlSession> entry : sqlSessions.entrySet()) {
                if (!DEFAULT_SQL_CONFIG_DATASOURCE.equals(entry.getKey())) continue;
                Configuration configuration = entry.getValue().getConfiguration();
                MappedStatement statement = DevBaseStatementFactory.build(configuration, injectMeta, type);
                configuration.addMappedStatement(statement);
            }
        };
    }


}
