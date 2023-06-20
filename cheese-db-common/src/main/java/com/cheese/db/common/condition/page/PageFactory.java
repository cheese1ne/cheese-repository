package com.cheese.db.common.condition.page;

/**
 * 分页构建工厂
 *
 * @author sobann
 */
public class PageFactory {

    /**
     * 创建基本的分页对象
     *
     * @param current
     * @param size
     * @param <T>
     * @return
     */
    public static <T> IPage<T> getPage(long current, long size) {
        DevBasePage<T> devBasePage = new DevBasePage<>();
        devBasePage.setCurrent(current);
        devBasePage.setSize(size);
        return devBasePage;
    }
}
