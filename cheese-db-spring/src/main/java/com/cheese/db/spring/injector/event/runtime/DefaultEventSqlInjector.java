package com.cheese.db.spring.injector.event.runtime;

import com.cheese.db.core.DevBaseConfiguration;
import com.cheese.db.spring.injector.DevBaseSqlInjector;
import com.cheese.db.spring.injector.DevBaseStatementFactory;
import com.cheese.db.spring.injector.event.InjectType;
import com.cheese.db.spring.injector.metadata.InjectMeta;
import com.cheese.db.spring.wrappers.DevBaseSqlSessions;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 默认的基于事件的sql注册器
 * 运行时进行注册,基于发布SqlInjectEvent事件
 *
 * @author sobann
 */
public class DefaultEventSqlInjector implements DevBaseSqlInjector {
    private static Logger logger = LoggerFactory.getLogger(DefaultEventSqlInjector.class);
    private final InjectType injectType;
    private final Map<String, List<InjectMeta>> injectMetaList;

    public DefaultEventSqlInjector(InjectType injectType, Map<String, List<InjectMeta>> injectMetaList) {
        this.injectType = injectType;
        this.injectMetaList = injectMetaList;
    }

    @Override
    public void inspectInject(DevBaseSqlSessions devBaseSqlSessions, Class<?> type) throws Exception {
        Map<String, SqlSession> sqlSessions = devBaseSqlSessions.getSqlSessions();
        for (Map.Entry<String, SqlSession> entry : sqlSessions.entrySet()) {
            //匹配项 根据数据源分组
            String dbKey = entry.getKey();
            List<InjectMeta> injectMetas = injectMetaList.get(dbKey);
            if (CollectionUtils.isEmpty(injectMetas)) continue;
            SqlSession sqlSession = entry.getValue();
            //获取到自己的配置类
            DevBaseConfiguration configuration = (DevBaseConfiguration) sqlSession.getConfiguration();
            for (InjectMeta injectMeta : injectMetas) {
                MappedStatement mappedStatement = DevBaseStatementFactory.build(configuration, injectMeta, type);
                configuration.addMappedStatement(mappedStatement);
                logger.debug("mappedStatement has registered, which code is {} ", mappedStatement.getId());
            }
        }
    }


    public static class Builder {

        private InjectType injectType;
        private List<InjectMeta> injectMetaList;

        public Builder(InjectType injectType, List<InjectMeta> injectMetaList) {
            this.injectType = injectType;
            this.injectMetaList = injectMetaList;
        }

        public DefaultEventSqlInjector build() {
            this.injectType = Optional.ofNullable(this.injectType).orElse(InjectType.ALL);
            this.injectMetaList = Optional.ofNullable(this.injectMetaList).orElse(Collections.emptyList());
            Map<String, List<InjectMeta>> injectMetaListGrop = injectMetaList.stream().filter(item -> Objects.nonNull(item.getDbKey())).collect(Collectors.groupingBy(InjectMeta::getDbKey));
            return new DefaultEventSqlInjector(injectType, injectMetaListGrop);
        }


        public InjectType getInjectType() {
            return injectType;
        }

        public void setInjectType(InjectType injectType) {
            this.injectType = injectType;
        }

        public List<InjectMeta> getInjectMetaList() {
            return injectMetaList;
        }

        public void setInjectMetaList(List<InjectMeta> injectMetaList) {
            this.injectMetaList = injectMetaList;
        }
    }
}
