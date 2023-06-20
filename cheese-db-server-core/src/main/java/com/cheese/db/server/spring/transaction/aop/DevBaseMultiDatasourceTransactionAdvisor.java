package com.cheese.db.server.spring.transaction.aop;

import com.cheese.db.server.spring.annotation.DevBaseMultiDataSourceTransactional;
import com.cheese.db.server.spring.wrapper.DevBaseDataSourceTransactionManagers;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 自定义切面行为
 * 先写一个简单的吧
 *
 * @author sobann
 */
public class DevBaseMultiDatasourceTransactionAdvisor extends StaticMethodMatcherPointcutAdvisor implements ApplicationContextAware {

    /**
     * 这里切面只针对@DevBaseMultiDataSourceTransactional
     * Transactional
     */
    private static final Class<? extends Annotation>[] TRANSACTION_ANNOTATION_CLASSES = new Class[]{DevBaseMultiDataSourceTransactional.class};

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if (isTransactionAnnotationPresent(method)) {
            return true;
        }
        try {
            method = targetClass.getMethod(method.getName(), method.getParameterTypes());
            return isTransactionAnnotationPresent(method) || isTransactionAnnotationPresent(targetClass);
        } catch (NoSuchMethodException ignored) {
        }
        return false;
    }

    /**
     * 如果类上标注了注解同样视为需要处理的方法
     *
     * @param targetClazz
     * @return
     */
    private boolean isTransactionAnnotationPresent(Class<?> targetClazz) {
        for (Class<? extends Annotation> annClass : TRANSACTION_ANNOTATION_CLASSES) {
            Annotation a = AnnotationUtils.findAnnotation(targetClazz, annClass);
            if (a != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 方法上标注的方法若配置了权限注解则视为需要aop处理
     *
     * @param method
     * @return
     */
    private boolean isTransactionAnnotationPresent(Method method) {
        for (Class<? extends Annotation> annClass : TRANSACTION_ANNOTATION_CLASSES) {
            Annotation a = AnnotationUtils.findAnnotation(method, annClass);
            if (a != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DevBaseDataSourceTransactionManagers transactionManagers = applicationContext.getBean(DevBaseDataSourceTransactionManagers.class);
        setAdvice(new DevBaseAnnotationsTransactionMethodInterceptor(transactionManagers));
    }
}
