package com.cheese.db.common.service;

/**
 * DevBaseService 实现提供者
 * 用与DevBaseService间的隔离、懒加载
 *
 * @author sobann
 */
public interface DevBaseServiceProvider {

    /**
     * 获取devbase服务实例
     *
     * @return
     */
    DevBaseService getDevBaseService();
}
