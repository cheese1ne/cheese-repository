package com.cheese.db.core.condition.simple.delete;

import com.cheese.db.core.condition.AbstractTableAction;
import com.cheese.db.core.condition.CommonSegmentProvider;
import com.cheese.db.core.enums.ActionType;
import com.cheese.db.core.enums.Comparator;
import com.cheese.db.core.enums.LikeType;
import com.cheese.db.core.enums.RangeType;

import java.util.HashMap;
import java.util.Map;

/**
 * 删除操作
 *
 * @author sobann
 */
public class DeleteTableAction extends AbstractTableAction {

    private Map<String, Object> param;
    private final CommonSegmentProvider commonSegmentProvider;

    public DeleteTableAction(String dbKey, String tableName) {
        super(dbKey, tableName);
        this.param = new HashMap<>(8);
        this.commonSegmentProvider = new CommonSegmentProvider();
    }

    public void putParam(String field, Object value) {
        this.param.put(field, value);
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> cdn) {
        this.param = cdn;
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
        return ActionType.DELETE;
    }

}
