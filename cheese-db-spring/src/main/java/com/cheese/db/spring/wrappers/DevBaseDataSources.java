package com.cheese.db.spring.wrappers;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 多数据源
 *
 * @author sobann
 */
public interface DevBaseDataSources {

    Map<String, DataSource> getSources();

    void put(String dbKey, DataSource dataSource);
}
