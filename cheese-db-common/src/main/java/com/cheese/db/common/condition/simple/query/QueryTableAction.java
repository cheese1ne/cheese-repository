package com.cheese.db.common.condition.simple.query;

import com.cheese.db.common.condition.CommonSegmentProvider;
import com.cheese.db.common.enums.ActionType;
import com.cheese.db.common.enums.Comparator;
import com.cheese.db.common.enums.LikeType;
import com.cheese.db.common.enums.RangeType;

import java.util.Map;

/**
 * 查询操作 使用mybatis的MapWrapper处理参数和返回
 *
 * @author sobann
 */
public class QueryTableAction extends AbstractQueryAction<Map<String, Object>> {

    private final CommonSegmentProvider commonSegmentProvider;

    public QueryTableAction(String dbKey, String tableName) {
        super(dbKey, tableName);
        this.commonSegmentProvider = new CommonSegmentProvider();
    }

    public void putLikeParam(String field, LikeType likeType, Object value) {
        commonSegmentProvider.putLikeParam(field, likeType, value);
    }

    public void putRangeParam(String field, RangeType rangeType, Object... rangeValues) {
        commonSegmentProvider.putRangeParam(field, rangeType, rangeValues);
    }

    public void putComparatorParam(String field, Comparator comparator, Object value) {
        commonSegmentProvider.putComparatorParam(field, comparator, value);
    }

    @Override
    public String getSqlSegment() {
        return commonSegmentProvider.supportSqlSegment();
    }

    @Override
    public ActionType getActionType() {
        return ActionType.SELECT;
    }
}
