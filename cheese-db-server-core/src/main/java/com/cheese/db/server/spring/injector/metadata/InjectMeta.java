package com.cheese.db.server.spring.injector.metadata;

import com.cheese.db.common.constant.DevBaseConstant;
import org.apache.ibatis.mapping.SqlCommandType;

/**
 * sql元数据 顶层接口
 *
 * @author sobann
 */
public interface InjectMeta extends DevBaseConstant {

    /**
     * 获取数据库标识
     *
     * @return
     */
    String getDbKey();

    /**
     * 设置数据库标识
     *
     * @param dbKey
     */
    void setDbKey(String dbKey);

    /**
     * 获取动态sql
     *
     * @return
     */
    String getContent();

    /**
     * 设置动态sql
     *
     * @param content
     */
    void setContent(String content);

    /**
     * 获取脚本唯一code
     *
     * @return
     */
    String getCode();

    /**
     * 设置脚本唯一code
     *
     * @param code
     */
    void setCode(String code);

    /**
     * 获取sql类型
     *
     * @return
     */
    SqlCommandType getSqlCommandType();

    /**
     * 设置sql类型
     *
     * @param sqlCommandType
     */
    void setSqlCommandType(SqlCommandType sqlCommandType);

    /**
     * 获取返回值类型
     *
     * @return
     */
    Class<?> getReturnType();

    /**
     * 设置返回值类型
     *
     * @param returnType
     */
    void setReturnType(Class<?> returnType);

    /**
     * 获取注册的持久层实例
     *
     * @return
     */
    Class<?> getMapperInterface();

    /**
     * 设置注册的持久层实例
     *
     * @param mapperInterface
     */
    void setMapperInterface(Class<?> mapperInterface);

    /**
     * 获取主键
     *
     * @return
     */
    String getKeyColumn();

    /**
     * 设置主键
     *
     * @param keyColumn
     */
    void setKeyColumn(String keyColumn);
}
