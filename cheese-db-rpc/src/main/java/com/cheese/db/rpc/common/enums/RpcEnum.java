package com.cheese.db.rpc.common.enums;

/**
 * @author sobann
 */
public enum RpcEnum {

    /**
     * feign
     */
    FEIGN("feign","com.cheese.db.rpc.feign.FeignRpcAutoConfiguration"),
    /**
     * dubbo
     */
    DUBBO("dubbo","com.cheese.db.rpc.dubbo.DubboRpcAutoConfiguration"),
    /**
     * mock
     */
    MOCK("mock","com.cheese.db.rpc.mock.MockRpcAutoConfiguration");

    RpcEnum(String type, String fullClassName) {
        this.type = type;
        this.fullClassName = fullClassName;
    }

    private final String type;
    private final String fullClassName;

    public String getFullClassName() {
        return this.fullClassName;
    }

    public String getType(){
        return this.type;
    }

    /**
     * 根据rpc类型获取全类名
     *
     * @param type
     * @return
     */
    public static String parseType(String type) {
        RpcEnum[] values = RpcEnum.values();
        for (RpcEnum rpcEnum : values) {
            if (rpcEnum.type.equals(type)) {
                return rpcEnum.getFullClassName();
            }
        }
        throw new IllegalArgumentException("未知的rpc类型：" + type);
    }
}
