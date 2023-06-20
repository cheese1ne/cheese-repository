package com.cheese.db.server.spring.injector;

import com.cheese.db.common.constant.DevBaseConstant;
import com.cheese.db.server.core.DevBaseConfiguration;
import com.cheese.db.server.spring.injector.metadata.InjectMeta;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlCommandType;
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
        KeyGenerator keyGenerator = new NoKeyGenerator();
        String keyProperty = BLANK_STR;
        String keyColumn = BLANK_STR;
        if (SqlCommandType.INSERT == injectMeta.getSqlCommandType() && ((DevBaseConfiguration)configuration).getKeyGenerator().equals(Jdbc3KeyGenerator.class)) {
            keyGenerator = new Jdbc3KeyGenerator();
            keyColumn = injectMeta.getKeyColumn();
            keyProperty = ((DevBaseConfiguration)configuration).getInsertKeyProperty();
        }
        MappedStatement ms = (new MappedStatement.Builder(configuration, statementId, sqlSource, injectMeta.getSqlCommandType())).keyGenerator(keyGenerator).keyProperty(keyProperty).keyColumn(keyColumn).resultMaps(new ArrayList<ResultMap>() {
            {
                this.add((new ResultMap.Builder(configuration, "defaultResultMap",
                        injectMeta.getReturnType(),
                        new ArrayList<>(ZERO))).build());
            }
        }).build();
        return ms;
    }
}
