package com.cheese.db.spring.injector.event;

/**
 * sql注册行为
 *  1.ALL注册所有
 *  2.SINGLE单个sql注册
 *  3.REFRESH重置
 *
 * @author sobann
 */
public enum InjectType {
    ALL, SINGLE, REFRESH
}
