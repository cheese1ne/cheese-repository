package com.cheese.db.core.condition.simple.delete;

import com.cheese.db.core.condition.AbstractTableAction;
import com.cheese.db.core.condition.query.LikeKeyValue;
import com.cheese.db.core.condition.query.ComparatorKeyValue;
import com.cheese.db.core.condition.query.RangeKeyValue;
import com.cheese.db.core.enums.ActionType;
import com.cheese.db.core.enums.Comparator;
import com.cheese.db.core.enums.LikeType;
import com.cheese.db.core.enums.RangeType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 删除操作
 *
 * @author sobann
 */
public class DeleteTableAction extends AbstractTableAction {

    private Map<String, Object> fieldCdn;
    private final List<RangeKeyValue> rangeCdn;
    private final List<LikeKeyValue> likeCdn;
    private final List<ComparatorKeyValue> comparatorCdn;

    public DeleteTableAction(String dbKey, String tableName) {
        super(dbKey, tableName);
        this.fieldCdn = new HashMap<>(8);
        this.rangeCdn = new ArrayList<>(8);
        this.likeCdn = new ArrayList<>(8);
        this.comparatorCdn = new ArrayList<>(8);
    }

    public void putCdn(String field, Object value) {
        this.fieldCdn.put(field, value);
    }

    public Map<String, Object> getCdn() {
        return fieldCdn;
    }

    public void setCdn(Map<String, Object> cdn) {
        this.fieldCdn = cdn;
    }

    public void putLikeCdn(String field, LikeType likeType, Object value) {
        likeCdn.add(new LikeKeyValue(field, value, likeType));
    }

    public void putRangeCdn(String field, RangeType rangeType, Object... rangeValues) {
        rangeCdn.add(new RangeKeyValue(field, rangeType, rangeValues));
    }

    public void putComparatorCdn(String field, Comparator comparator, Object value) {
        comparatorCdn.add(new ComparatorKeyValue(field, value, comparator));
    }

    @Override
    public String getSqlSegment() {
        if (comparatorCdn.isEmpty() && likeCdn.isEmpty() && rangeCdn.isEmpty()) {
            return super.getSqlSegment();
        }
        StringBuilder segmentBuilder = new StringBuilder();
        if (!rangeCdn.isEmpty()) {
            String inSegment = rangeCdn.stream().map(item -> String.format(" AND %s %s ", item.getKey(), String.format(item.getRangeType().getSegment(), Arrays.stream(item.getValues()).map(String::valueOf).sorted().collect(Collectors.joining(","))))).collect(Collectors.joining(" "));
            segmentBuilder.append(inSegment);
        }
        if (!likeCdn.isEmpty()) {
            String likeSegment = likeCdn.stream().map(item -> String.format(" AND %s LIKE '%s'", item.getKey(), String.format(item.getLikeType().getSegment(), item.getValue()))).collect(Collectors.joining(" "));
            segmentBuilder.append(likeSegment);
        }
        if (!comparatorCdn.isEmpty()) {
            String comparatorSegment = comparatorCdn.stream().map(item -> String.format(" AND %s %s '%s' ", item.getKey(), item.getRelation().getToken(), item.getValue())).collect(Collectors.joining(" "));
            segmentBuilder.append(comparatorSegment);
        }
        return segmentBuilder.toString();
    }

    @Override
    public ActionType getActionType() {
        return ActionType.DELETE;
    }

}
