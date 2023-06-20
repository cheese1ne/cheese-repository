package com.cheese.db.server.spring.injector.metadata.simple;

import com.cheese.db.server.spring.injector.metadata.InjectMeta;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.Map;

/**
 * 默认的sql元数据，
 * 查询、新增、修改、删除的标识分别为0,1,2,3
 * 此类可以根据数据库配置情况进行自定义
 *
 * @author sobann
 */
public class DefaultInjectMeta implements InjectMeta {

    private String dbKey;

    private String content;

    private String code;

    private int sqlCommandTypeCode;

    private SqlCommandType sqlCommandType;

    private Class<?> returnType;

    private Class<?> mapperInterface;

    private String keyColumn;

    public int getSqlCommandTypeCode() {
        return sqlCommandTypeCode;
    }

    public void setSqlCommandTypeCode(int sqlCommandTypeCode) {
        this.sqlCommandTypeCode = sqlCommandTypeCode;
        switch (sqlCommandTypeCode) {
            case ZERO:
                this.sqlCommandType = SqlCommandType.SELECT;
                break;
            case ONE:
                this.sqlCommandType = SqlCommandType.INSERT;
                break;
            case TWO:
                this.sqlCommandType = SqlCommandType.UPDATE;
                break;
            case THREE:
                this.sqlCommandType = SqlCommandType.DELETE;
                break;
        }
    }

    @Override
    public String getDbKey() {
        return dbKey;
    }

    @Override
    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    @Override
    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    @Override
    public Class<?> getReturnType() {
        if (returnType == null) return Map.class;
        return returnType;
    }

    @Override
    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    @Override
    public Class<?> getMapperInterface() {
        return mapperInterface;
    }

    @Override
    public void setMapperInterface(Class<?> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public String getKeyColumn() {
        return keyColumn;
    }

    @Override
    public void setKeyColumn(String keyProperty) {
        this.keyColumn = keyProperty;
    }
}
