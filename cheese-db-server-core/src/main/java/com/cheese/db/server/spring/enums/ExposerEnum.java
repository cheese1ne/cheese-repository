package com.cheese.db.server.spring.enums;

/**
 * @author sobann
 */
public enum ExposerEnum {
    /**
     * 默认暴露方式，http
     */
    DEFAULT("default", "com.cheese.db.server.expore.http.HttpDevBaseResourceAutoConfiguration"),
    /**
     * DUBBO SOA服务暴露
     */
    DUBBO("dubbo", "com.cheese.db.server.expore.dubbo.DubboDevBaseResourceAutoConfiguration"),
    /**
     * HTTP 接口暴露(springMvc)
     */
    HTTP("http", "com.cheese.db.server.expore.http.HttpDevBaseResourceAutoConfiguration");


    ExposerEnum(String type, String fullClassName) {
        this.type = type;
        this.fullClassName = fullClassName;
    }

    private final String type;
    private final String fullClassName;

    public String getFullClassName() {
        return this.fullClassName;
    }

    public String getType() {
        return this.type;
    }

    /**
     * 根据selector类型获取全类名
     *
     * @param type
     * @return
     */
    public static String parseType(String type) {
        ExposerEnum[] values = ExposerEnum.values();
        for (ExposerEnum selectorEnum : values) {
            if (selectorEnum.type.equals(type)) {
                return selectorEnum.getFullClassName();
            }
        }
        throw new IllegalArgumentException("未知的exposer类型：" + type);
    }
}
