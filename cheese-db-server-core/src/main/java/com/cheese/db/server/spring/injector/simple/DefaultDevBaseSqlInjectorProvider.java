package com.cheese.db.server.spring.injector.simple;

import com.cheese.db.common.constant.DevBaseConstant;
import com.cheese.db.server.spring.injector.DevBaseSqlInjector;
import com.cheese.db.server.spring.injector.DevBaseSqlInjectorProvider;
import com.cheese.db.server.spring.injector.DevBaseStatementFactory;
import com.cheese.db.server.spring.injector.metadata.simple.DefaultInjectMeta;
import com.cheese.db.server.spring.injector.metadata.simple.MysqlTableMeta;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.util.Map;

/**
 * 默认的sqlInjector提供者
 * 1.项目启动时
 * a.注册sys_config_sql等数据的查询功能
 * b.基础增上改查扩展 (增加表的增删改元数据配置表 todo)
 *
 * @author sobann
 */
public class DefaultDevBaseSqlInjectorProvider implements DevBaseSqlInjectorProvider, DevBaseConstant {

    private static DefaultInjectMeta injectMeta = new DefaultInjectMeta();
    private static DefaultInjectMeta tableMeta = new DefaultInjectMeta();

    static {
        // 配置的sql
        injectMeta.setCode(DEFAULT_SQL_CONFIG_CODE);
        injectMeta.setSqlCommandType(SqlCommandType.SELECT);
        injectMeta.setReturnType(DefaultInjectMeta.class);
        injectMeta.setDbKey(DEFAULT_SQL_CONFIG_DATASOURCE);
        injectMeta.setContent(DEFAULT_MYSQL_SYS_CONFIG_SQL);

        // 表格元数据sql
        tableMeta.setCode(DEFAULT_TABLE_META_CONFIG_CODE);
        tableMeta.setSqlCommandType(SqlCommandType.SELECT);
        tableMeta.setReturnType(MysqlTableMeta.class);
        tableMeta.setDbKey(ALL_IDENTIFIER);
        tableMeta.setContent(DEFAULT_MYSQL_TABLE_META_CONFIG_SQL);
    }

    @Override
    public DevBaseSqlInjector getDevBaseSqlInjector() {
        //默认注册的sql目前仅有sys_config_sql的内容
        return (devBaseSqlSessions, type) -> {
            Map<String, SqlSession> sqlSessions = devBaseSqlSessions.getSqlSessions();
            for (Map.Entry<String, SqlSession> entry : sqlSessions.entrySet()) {
                Configuration configuration = entry.getValue().getConfiguration();
                MappedStatement tableMetaStatement = DevBaseStatementFactory.build(configuration, tableMeta, type);
                configuration.addMappedStatement(tableMetaStatement);
                if (!DEFAULT_SQL_CONFIG_DATASOURCE.equals(entry.getKey())) continue;
                MappedStatement extraSqlStatement = DevBaseStatementFactory.build(configuration, injectMeta, type);
                configuration.addMappedStatement(extraSqlStatement);
            }

        };
    }


}
