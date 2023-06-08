package com.cheese.db.spring.transaction.aspectj;

import cn.hutool.core.lang.Pair;
import com.cheese.db.spring.annotation.DevBaseMultiDataSourceTransactional;
import com.cheese.db.spring.wrappers.DevBaseDataSourceTransactionManagers;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Stack;

/**
 * 多数据源事务管理器切面
 * <p>
 * todo 切面类定义为一个接口
 * 整合spring的事务注解Transactional，事务的传播机制和隔离机制使用此注解内的内容
 *
 *
 * @author sobann
 */
@Aspect
public class DevBaseMultiDataSourceTransactionalAspect implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 线程本地变量：使用栈保存事务管理器和状态是为了达到后进先出的效果
     */
    private static final ThreadLocal<Stack<Pair<DataSourceTransactionManager, TransactionStatus>>> THREAD_LOCAL = new ThreadLocal<>();


    /**
     * 切面
     */
    @Pointcut("@annotation(com.cheese.db.spring.annotation.DevBaseMultiDataSourceTransactional)")
    public void pointcut() {
    }

    /**
     * 声明事务
     *
     * @param transactional 注解
     */
    @Before("pointcut() && @annotation(transactional)")
    public void before(DevBaseMultiDataSourceTransactional transactional) {
        // 根据设置的事务名称按顺序声明，并放到ThreadLocal里
        System.err.println("Prepare to handle multi-source transaction management method...");
        String[] transactionDbKeys = transactional.transactionDbKeys();
        Stack<Pair<DataSourceTransactionManager, TransactionStatus>> pairStack = new Stack<>();
        DevBaseDataSourceTransactionManagers transactionManagers = applicationContext.getBean(DevBaseDataSourceTransactionManagers.class);

        for (String dbKey : transactionDbKeys) {
            DataSourceTransactionManager dataSourceTransactionManager = transactionManagers.get(dbKey);
            TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(getTransactionDefinition());
            pairStack.push(new Pair<>(dataSourceTransactionManager, transactionStatus));
        }
        THREAD_LOCAL.set(pairStack);
    }


    /**
     * 提交事务
     */
    @AfterReturning("pointcut()")
    public void afterReturning() {
        System.err.println("No exception, ready to commit transaction...");
        // 栈顶弹出（后进先出）
        Stack<Pair<DataSourceTransactionManager, TransactionStatus>> pairStack = THREAD_LOCAL.get();
        while (!pairStack.empty()) {
            Pair<DataSourceTransactionManager, TransactionStatus> pair = pairStack.pop();
            pair.getKey().commit(pair.getValue());
        }
        THREAD_LOCAL.remove();
    }

    /**
     * 回滚事务
     */
    @AfterThrowing(value = "pointcut()")
    public void afterThrowing() {
        System.err.println("An exception occurs and the transaction is ready to be rolled back...");
        // ※栈顶弹出（后进先出）
        Stack<Pair<DataSourceTransactionManager, TransactionStatus>> pairStack = THREAD_LOCAL.get();
        while (!pairStack.empty()) {
            Pair<DataSourceTransactionManager, TransactionStatus> pair = pairStack.pop();
            pair.getKey().rollback(pair.getValue());
        }
        THREAD_LOCAL.remove();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**
     * 获取事务定义,最基本的事务定义
     * 因为异常捕获是通过切点完成的，所以使用的是definition而不是对异常有条件的TransactionAttribute
     * 采用数据库的默认隔离级别
     *  mysql 可重复读
     *  oracle 读已提交
     *
     * @return
     */
    private DefaultTransactionDefinition getTransactionDefinition() {
        //设置事务定义器
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // 非只读模式
        def.setReadOnly(false);
        // 事务隔离级别：采用数据库的默认隔离级别
        def.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        // 事务传播行为：创建或加入事务
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return def;
    }
}
