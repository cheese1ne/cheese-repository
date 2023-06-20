package com.cheese.db.server.spring.transaction.aop;

import cn.hutool.core.lang.Pair;
import com.cheese.db.common.exception.TransactionException;
import com.cheese.db.server.spring.annotation.DevBaseMultiDataSourceTransactional;
import com.cheese.db.server.spring.wrapper.DevBaseDataSourceTransactionManagers;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Stack;

/**
 * 切面拦截器
 *
 * @author sobann
 */
public class DevBaseAnnotationsTransactionMethodInterceptor implements MethodInterceptor {

    private static final Class<DevBaseMultiDataSourceTransactional> HANDLE_ANNOTATION = DevBaseMultiDataSourceTransactional.class;

    private final DevBaseDataSourceTransactionManagers devBaseDataSourceTransactionManagers;

    public DevBaseAnnotationsTransactionMethodInterceptor(DevBaseDataSourceTransactionManagers devBaseDataSourceTransactionManagers) {
        this.devBaseDataSourceTransactionManagers = devBaseDataSourceTransactionManagers;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Annotation annotation = this.getAnnotation(methodInvocation);
        if (Objects.isNull(annotation)) {
            return methodInvocation.proceed();
        }
        // 针对Transactional的解析，可以利用SpringTransactionAnnotationParser.parseTransactionAnnotation，多数据源的回滚异常不能使用Transactional，要定义在DevBaseMultiDataSourceTransactional中
        DevBaseMultiDataSourceTransactional multiDataSourceTransactional = (DevBaseMultiDataSourceTransactional) annotation;
        String[] transactionDbKeys = multiDataSourceTransactional.transactionDbKeys();
        Stack<Pair<DataSourceTransactionManager, TransactionStatus>> pairStack = new Stack<>();
        try {

            for (String dbKey : transactionDbKeys) {
                DataSourceTransactionManager dataSourceTransactionManager = devBaseDataSourceTransactionManagers.get(dbKey);
                TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(getTransactionDefinition());
                pairStack.push(new Pair<>(dataSourceTransactionManager, transactionStatus));
            }
            Object proceed = methodInvocation.proceed();
            System.err.println("No exception, ready to commit transaction...");
            while (!pairStack.empty()) {
                Pair<DataSourceTransactionManager, TransactionStatus> pair = pairStack.pop();
                pair.getKey().commit(pair.getValue());
            }
            return proceed;
        } catch (Exception e) {
            System.err.println("An exception occurs and the transaction is ready to be rolled back...");
            while (!pairStack.empty()) {
                Pair<DataSourceTransactionManager, TransactionStatus> pair = pairStack.pop();
                pair.getKey().rollback(pair.getValue());
            }
            throw new TransactionException(e);
        }
    }


    /**
     * 从方法或类上解析出DevBaseMultiDataSourceTransactional
     * 方法优先
     *
     * @param methodInvocation
     * @return
     */
    protected Annotation getAnnotation(MethodInvocation methodInvocation) {
        DevBaseMultiDataSourceTransactional multiDataSourceTransactional = AnnotationUtils.findAnnotation(methodInvocation.getMethod(), HANDLE_ANNOTATION);
        if (multiDataSourceTransactional == null) {
            Class<?> targetClass = methodInvocation.getThis().getClass();
            Method method = ClassUtils.getMostSpecificMethod(methodInvocation.getMethod(), targetClass);
            multiDataSourceTransactional = AnnotationUtils.findAnnotation(method, HANDLE_ANNOTATION);
        }
        return multiDataSourceTransactional;
    }


    /**
     * 获取事务定义,最基本的事务定义
     * 因为异常捕获是通过切点完成的，所以使用的是definition而不是对异常有条件的TransactionAttribute
     * 采用数据库的默认隔离级别
     * mysql 可重复读
     * oracle 读已提交
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
