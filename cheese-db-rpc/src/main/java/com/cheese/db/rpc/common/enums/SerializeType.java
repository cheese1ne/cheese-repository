package com.cheese.db.rpc.common.enums;

/**
 * serialize 类型
 *
 * @author sobann
 */
public enum SerializeType {

    /**
     * default
     */
    DEFAULT("default", "com.cheese.db.rpc.serializer.hessian.HessianSerializeAutoConfiguration"),
    /**
     * hessain2
     */
    HESSIAN("hessian", "com.cheese.db.rpc.serializer.hessian.HessianSerializeAutoConfiguration"),
    /**
     * json
     */
    JSON("json", "com.cheese.db.rpc.serializer.json.JsonSerializeAutoConfiguration");

    SerializeType(String type, String fullClassName) {
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
     * 根据rpc类型获取全类名
     *
     * @param type
     * @return
     */
    public static String parseType(String type) {
        SerializeType[] values = SerializeType.values();
        for (SerializeType serializeEnum : values) {
            if (serializeEnum.type.equals(type)) {
                return serializeEnum.getFullClassName();
            }
        }
        throw new IllegalArgumentException("未知的serialize类型：" + type);
    }
}
