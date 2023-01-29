package com.cheese.db.spring.injector.metadata;

import com.cheese.db.core.support.DevBaseConstant;
import org.apache.ibatis.mapping.SqlCommandType;

/**
 * sql元数据 顶层接口
 *
 * @author sobann
 */
public interface InjectMeta extends DevBaseConstant {

    String getDbKey();

    void setDbKey(String dbKey);

    String getContent();

    void setContent(String content);

    String getCode();

    void setCode(String code);

    SqlCommandType getSqlCommandType();

    void setSqlCommandType(SqlCommandType sqlCommandType);

    Class<?> getReturnType();

    void setReturnType(Class<?> returnType);

    Class<?> getMapperInterface();

    void setMapperInterface(Class<?> mapperInterface);
}
