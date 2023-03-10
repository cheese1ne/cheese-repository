package com.cheese.db.spring.injector;

import com.cheese.db.core.support.DevBaseConstant;
import com.cheese.db.spring.injector.metadata.InjectMeta;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.ArrayList;
import java.util.Map;

/**
 * Statement构建工厂类
 *
 * @author sobann
 */
public class DevBaseStatementFactory implements DevBaseConstant {

    public static MappedStatement build(Configuration configuration, InjectMeta injectMeta, Class<?> type) {
        LanguageDriver languageDriver = new XMLLanguageDriver();
        String scriptContent = String.format(SCRIPT_TAG_TEMPLATE, injectMeta.getContent());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, scriptContent, Map.class);
        String statementId = String.format(MAPPED_STATEMENT_ID_TEMPLATE, type.getName(), injectMeta.getCode());
        MappedStatement ms = (new MappedStatement.Builder(configuration, statementId, sqlSource, injectMeta.getSqlCommandType())).resultMaps(new ArrayList<ResultMap>() {
            {
                this.add((new org.apache.ibatis.mapping.ResultMap.Builder(configuration, "defaultResultMap",
                        injectMeta.getReturnType(),
                        new ArrayList<>(ZERO))).build());
            }
        }).build();
        return ms;
    }
}
