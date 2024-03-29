package com.cheese.db.spring.utils;

import org.springframework.util.StringUtils;

/**
 * 使用 mybatis-plus 工具
 *
 * @author sobann
 */
public final class SqlScriptUtils extends StringUtils {

    private SqlScriptUtils() {
        // ignore
    }

    public static final String QUOTE = "\"";
    public static final String NEWLINE = "\n";
    public static final String RIGHT_CHEV = ">";
    public static final String HASH_LEFT_BRACE = "#{";
    public static final String RIGHT_BRACE = "}";
    public static final String DOLLAR_LEFT_BRACE = "${";


    /**
     * <p>
     * 获取 带 if 标签的脚本
     * </p>
     *
     * @param sqlScript sql 脚本片段
     * @return if 脚本
     */
    public static String convertIf(final String sqlScript, final String ifTest, boolean newLine) {
        String newSqlScript = sqlScript;
        if (newLine) {
            newSqlScript = NEWLINE + newSqlScript + NEWLINE;
        }
        return String.format("<if test=\"%s\">%s</if>", ifTest, newSqlScript);
    }

    /**
     * <p>
     * 获取 带 trim 标签的脚本
     * </p>
     *
     * @param sqlScript       sql 脚本片段
     * @param prefix          以...开头
     * @param suffix          以...结尾
     * @param prefixOverrides 干掉最前一个...
     * @param suffixOverrides 干掉最后一个...
     * @return trim 脚本
     */
    public static String convertTrim(final String sqlScript, final String prefix, final String suffix,
                                     final String prefixOverrides, final String suffixOverrides) {
        StringBuilder sb = new StringBuilder("<trim");
        if (!StringUtils.isEmpty(prefix)) {
            sb.append(" prefix=\"").append(prefix).append(QUOTE);
        }
        if (!StringUtils.isEmpty(suffix)) {
            sb.append(" suffix=\"").append(suffix).append(QUOTE);
        }
        if (!StringUtils.isEmpty(prefixOverrides)) {
            sb.append(" prefixOverrides=\"").append(prefixOverrides).append(QUOTE);
        }
        if (!StringUtils.isEmpty(suffixOverrides)) {
            sb.append(" suffixOverrides=\"").append(suffixOverrides).append(QUOTE);
        }
        return sb.append(RIGHT_CHEV).append(NEWLINE).append(sqlScript).append(NEWLINE).append("</trim>").toString();
    }

    /**
     * <p>
     * 生成 choose 标签的脚本
     * </p>
     *
     * @param whenTest  when 内 test 的内容
     * @param otherwise otherwise 内容
     * @return choose 脚本
     */
    public static String convertChoose(final String whenTest, final String whenSqlScript, final String otherwise) {
        return "<choose>" + NEWLINE
                + "<when test=\"" + whenTest + QUOTE + RIGHT_CHEV + NEWLINE
                + whenSqlScript + NEWLINE + "</when>" + NEWLINE
                + "<otherwise>" + otherwise + "</otherwise>" + NEWLINE
                + "</choose>";
    }

    /**
     * <p>
     * 生成 foreach 标签的脚本
     * </p>
     *
     * @param sqlScript  foreach 内部的 sql 脚本
     * @param collection collection
     * @param index      index
     * @param item       item
     * @param separator  separator
     * @return foreach 脚本
     */
    public static String convertForeach(final String sqlScript, final String collection, final String index,
                                        final String item, final String separator) {
        StringBuilder sb = new StringBuilder("<foreach");
        if (!StringUtils.isEmpty(collection)) {
            sb.append(" collection=\"").append(collection).append(QUOTE);
        }
        if (!StringUtils.isEmpty(index)) {
            sb.append(" index=\"").append(index).append(QUOTE);
        }
        if (!StringUtils.isEmpty(item)) {
            sb.append(" item=\"").append(item).append(QUOTE);
        }
        if (!StringUtils.isEmpty(separator)) {
            sb.append(" separator=\"").append(separator).append(QUOTE);
        }
        return sb.append(RIGHT_CHEV).append(NEWLINE).append(sqlScript).append(NEWLINE).append("</foreach>").toString();
    }

    /**
     * <p>
     * 生成 where 标签的脚本
     * </p>
     *
     * @param sqlScript where 内部的 sql 脚本
     * @return where 脚本
     */
    public static String convertWhere(final String sqlScript) {
        return "<where>" + NEWLINE + sqlScript + NEWLINE + "</where>";
    }

    /**
     * <p>
     * 生成 set 标签的脚本
     * </p>
     *
     * @param sqlScript set 内部的 sql 脚本
     * @return set 脚本
     */
    public static String convertSet(final String sqlScript) {
        return "<set>" + NEWLINE + sqlScript + NEWLINE + "</set>";
    }

    /**
     * <p>
     * 安全入参:  #{入参}
     * </p>
     *
     * @param param 入参
     * @return 脚本
     */
    public static String safeParam(final String param) {
        return HASH_LEFT_BRACE + param + RIGHT_BRACE;
    }

    /**
     * <p>
     * 非安全入参:  ${入参}
     * </p>
     *
     * @param param 入参
     * @return 脚本
     */
    public static String unSafeParam(final String param) {
        return DOLLAR_LEFT_BRACE + param + RIGHT_BRACE;
    }
}