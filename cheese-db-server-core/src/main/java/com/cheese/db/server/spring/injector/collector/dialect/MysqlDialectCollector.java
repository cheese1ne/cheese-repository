package com.cheese.db.server.spring.injector.collector.dialect;

import com.cheese.db.common.condition.Actions;
import com.cheese.db.common.condition.load.LoadAction;
import com.cheese.db.common.constant.DevBaseConstant;
import com.cheese.db.server.core.mapper.DB;
import com.cheese.db.server.spring.enums.DialectType;
import com.cheese.db.server.spring.injector.collector.TableInjectMetaCollector;
import com.cheese.db.server.spring.injector.collector.method.*;
import com.cheese.db.server.spring.injector.metadata.InjectMeta;
import com.cheese.db.server.spring.injector.metadata.TableMeta;
import com.cheese.db.server.spring.injector.metadata.simple.MysqlTableMeta;
import com.cheese.db.server.spring.support.DevBaseApplicationContextSupport;
import com.cheese.db.server.spring.support.DevBaseTableMetaSupport;

import java.util.*;
import java.util.stream.Collectors;

/**
 * mysql元数据提取
 *
 * @author sobann
 */
public class MysqlDialectCollector extends TableInjectMetaCollector implements DevBaseConstant {

    private List<AbstractMethod> injectMethods;

    private DB db;

    public MysqlDialectCollector() {
        this.injectMethods = new ArrayList<>();
        injectMethods.add(new Insert());
        injectMethods.add(new Delete());
        injectMethods.add(new Select());
        injectMethods.add(new Update());
    }

    public void addInjectMethods(AbstractMethod... methods) {
        if (Objects.isNull(injectMethods)) {
            this.injectMethods = new ArrayList<>();
        }
        this.injectMethods.addAll(Arrays.asList(methods));
    }

    public List<AbstractMethod> getInjectMethods() {
        return injectMethods;
    }

    @Override
    protected List<InjectMeta> getInjectMetas(String schema) {
        if (db == null) {
            db = DevBaseApplicationContextSupport.getBean(DB.class);
        }
        LoadAction action = Actions.getLoad(DEFAULT_SQL_CONFIG_DATASOURCE, DEFAULT_TABLE_META_CONFIG_CODE);
        action.putParam(TABLE_SCHEMA_KEY, schema);
        List<MysqlTableMeta> tableMetaList = db.doActionGetList(action, MysqlTableMeta.class);
        if (Objects.isNull(tableMetaList) || tableMetaList.size() <= ZERO) {
            return Collections.emptyList();
        }
        List<InjectMeta> totalInjectMetas = new ArrayList<>();
        // 按照表名分组
        Map<String, List<MysqlTableMeta>> tableMetaListMap = tableMetaList.stream().collect(Collectors.groupingBy(MysqlTableMeta::getTableName));
        for (String tableName : tableMetaListMap.keySet()) {
            final List<MysqlTableMeta> oneTableMetaList = tableMetaListMap.get(tableName);
            TableMeta tablePrimary = findPrimaryTableMeta(schema, tableName, oneTableMetaList);
            if (Objects.isNull(tablePrimary)) continue;
            for (AbstractMethod injectMethod : getInjectMethods()) {
                InjectMeta injectMeta = injectMethod.buildInjectMeta(DialectType.Mysql, schema, tableName, tablePrimary, oneTableMetaList);
                if (Objects.isNull(injectMeta)) continue;
                totalInjectMetas.add(injectMeta);
            }
        }
        return totalInjectMetas;
    }


    /**
     * 获取表格元数据中的主键
     *
     * @param tableMetas
     * @return
     */
    protected TableMeta findPrimaryTableMeta(String schema, String tableName, List<MysqlTableMeta> tableMetas) {
        TableMeta primaryMeta = DevBaseTableMetaSupport.obtain(schema, tableName);
        if (Objects.nonNull(primaryMeta)) {
            return primaryMeta;
        }

        final MysqlTableMeta tablePrimary = tableMetas.stream().filter(item -> Objects.equals("PRI", item.getColumnKey())).findFirst().orElse(null);
        if (Objects.isNull(tablePrimary)) {
            return null;
        }
        DevBaseTableMetaSupport.bind(schema, tableName, tablePrimary);
        return tablePrimary;
    }

}
