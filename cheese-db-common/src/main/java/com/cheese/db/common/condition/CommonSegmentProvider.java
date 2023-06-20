package com.cheese.db.common.condition;

import com.cheese.db.common.condition.query.ComparatorKeyValue;
import com.cheese.db.common.condition.query.LikeKeyValue;
import com.cheese.db.common.condition.query.RangeKeyValue;
import com.cheese.db.common.enums.Comparator;
import com.cheese.db.common.enums.LikeType;
import com.cheese.db.common.enums.RangeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 对于查询、修改、删除通用的sql片段的包装
 *
 * @author sobann
 */
public class CommonSegmentProvider {

    private static final String NO_SEGMENT = null;

    private final List<RangeKeyValue> rangeCdn;
    private final List<LikeKeyValue> likeCdn;
    private final List<ComparatorKeyValue> comparatorCdn;

    public CommonSegmentProvider() {
        this.rangeCdn = new ArrayList<>(8);
        this.likeCdn = new ArrayList<>(8);
        this.comparatorCdn = new ArrayList<>(8);
    }

    public void putLikeParam(String field, LikeType likeType, Object value) {
        likeCdn.add(new LikeKeyValue(field, value, likeType));
    }

    public void putRangeParam(String field, RangeType rangeType, Object... rangeValues) {
        rangeCdn.add(new RangeKeyValue(field, rangeType, rangeValues));
    }

    public void putComparatorParam(String field, Comparator comparator, Object value) {
        comparatorCdn.add(new ComparatorKeyValue(field, value, comparator));
    }

    public String supportSqlSegment() {
        if (comparatorCdn.isEmpty() && likeCdn.isEmpty() && rangeCdn.isEmpty()) {
            return NO_SEGMENT;
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
}
