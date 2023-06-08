package com.cheese.db.enums;

/**
 * 事务方式枚举
 *
 * @author sobann
 */
public enum TransactionEnum {

    /**
     * 默认的事务拦截方式 使用aspectj
     */
    DEFAULT("default", "com.cheese.db.spring.transaction.aspectj.DevBaseMultiDataSourceTransactionalAspect"),

    /**
     * aspectj的事务拦截方式，比较灵活
     */
    ASPECTJ("aspectj", "com.cheese.db.spring.transaction.aspectj.DevBaseMultiDataSourceTransactionalAspect"),

    /**
     * advisor事务的拦截方式
     */
    ADVISOR("advisor", "com.cheese.db.spring.transaction.aop.DevBaseMultiDatasourceTransactionAdvisor");


    TransactionEnum(String type, String fullClassName) {
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
     * 根据transactionType类型获取全类名
     *
     * @param type
     * @return
     */
    public static String parseType(String type) {
        TransactionEnum[] values = TransactionEnum.values();
        for (TransactionEnum selectorEnum : values) {
            if (selectorEnum.type.equalsIgnoreCase(type)) {
                return selectorEnum.getFullClassName();
            }
        }
        throw new IllegalArgumentException("unknown transaction type:" + type);
    }

}
