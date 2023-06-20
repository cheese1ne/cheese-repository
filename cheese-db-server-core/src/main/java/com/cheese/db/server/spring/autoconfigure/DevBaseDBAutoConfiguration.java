package com.cheese.db.server.spring.autoconfigure;

import com.alibaba.druid.pool.DruidDataSource;
import com.cheese.db.common.condition.Action;
import com.cheese.db.common.condition.manager.DevBaseActionManager;
import com.cheese.db.common.service.DevBaseService;
import com.cheese.db.server.core.DevBaseConfiguration;
import com.cheese.db.server.core.mapper.DB;
import com.cheese.db.server.core.props.DataSourceConfig;
import com.cheese.db.server.core.props.MybatisConfig;
import com.cheese.db.server.expore.simple.SimpleDevBaseService;
import com.cheese.db.server.spring.annotation.DevBaseMappers;
import com.cheese.db.server.spring.injector.DevBaseSqlInjector;
import com.cheese.db.server.spring.injector.collector.SysSqlConfigInjectMetaCollector;
import com.cheese.db.server.spring.injector.event.DevBaseSqlInjectListener;
import com.cheese.db.server.spring.injector.simple.DefaultDevBaseSqlInjectorProvider;
import com.cheese.db.server.spring.props.DevBaseDBProps;
import com.cheese.db.server.spring.support.DatasourceContextSupport;
import com.cheese.db.server.spring.wrapper.DevBaseDataSourceTransactionManagers;
import com.cheese.db.server.spring.wrapper.DevBaseDataSources;
import com.cheese.db.server.spring.wrapper.DevBaseSqlSessionFactories;
import com.cheese.db.server.spring.wrapper.DevBaseSqlSessions;
import com.cheese.db.server.spring.wrapper.simple.DefaultDevBaseDataSourceTransactionManagers;
import com.cheese.db.server.spring.wrapper.simple.DefaultDevBaseDataSources;
import com.cheese.db.server.spring.wrapper.simple.DefaultDevBaseSqlSessionFactories;
import com.cheese.db.server.spring.wrapper.simple.DefaultDevBaseSqlSessions;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 依赖mybatis、spring-tx、aspectjweaver、以及hutool工具类
 * 若不需要DB相关功能配置通过配置devbase.db.enabled=false关闭
 * <p>
 * tip:多数据源默认加载方式使用原devbase的方式
 * 如有其他配置方法请自行定义
 *
 * @author sobann
 */
@Configuration
@EnableConfigurationProperties({DevBaseDBProps.class})
@ConditionalOnClass({SqlSessionFactory.class, SpringManagedTransaction.class, Aspect.class})
@ConditionalOnProperty(name = "devbase-db.server.enabled", matchIfMissing = true)
public class DevBaseDBAutoConfiguration implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(DevBaseDBAutoConfiguration.class);

    private ApplicationContext applicationContext;

    /**
     * 默认的多数据源wrapper
     */
    @Bean
    @ConditionalOnMissingBean
    public DevBaseDataSources devBaseDataSources(DevBaseDBProps properties) {
        //默认通过hutool配置的方式加载数据源
        DevBaseDataSources dataSources = new DefaultDevBaseDataSources();
        Map<String, DataSourceConfig> dataSourcesProps = properties.getDataSources();
        dataSourcesProps.forEach((key, config) -> {
            dataSources.put(config.getSchemeKey(), createDataSource(config));
            DatasourceContextSupport.DatasourceContext context = new DatasourceContextSupport.DatasourceContext();
            context.setConfigDataSource(properties.getConfigDataSource());
            context.setDataSources(dataSourcesProps);
            DatasourceContextSupport.set(context);
        });
        logger.info("prepare to initialize default devBaseDataSources");
        return dataSources;
    }

    /**
     * 默认创建druid数据源
     *
     * @param config
     * @return
     */
    private DataSource createDataSource(DataSourceConfig config) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(config.getUrl());
        dataSource.setDriverClassName(config.getDriverClassName());
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        dataSource.setInitialSize(config.getInitialSize());
        dataSource.setMinIdle(config.getMinIdle());
        dataSource.setMaxActive(config.getMaxActive());
        dataSource.setMaxWait(config.getMaxWait());
        return dataSource;
    }

    /**
     * 默认的事务管理器wrapper 事务交给spring上下文进行管理
     */
    @Bean
    @ConditionalOnMissingBean
    public DevBaseDataSourceTransactionManagers devBaseDataSourceTransactionManagers(DevBaseDataSources devBaseDataSources) {
        DevBaseDataSourceTransactionManagers managers = new DefaultDevBaseDataSourceTransactionManagers();
        devBaseDataSources.getSources().forEach((key, dataSource) -> {
            managers.put(key, new DataSourceTransactionManager(dataSource));
        });
        logger.info("prepare to initialize default devBaseDataSourceTransactionManagers");
        return managers;
    }

    /**
     * 默认的回话工厂wrapper
     * 事务工厂SpringManagedTransactionFactory
     * 使用需要通过spring-tx中sqlSessionHolder获取事务管理器创建的sqlSession来完成事务功能
     */
    @Bean
    @ConditionalOnMissingBean
    public DevBaseSqlSessionFactories devBaseSqlSessionFactories(DevBaseDataSources devBaseDataSources, DevBaseDBProps properties, ObjectProvider<DevBaseMappersInterceptorProvider> interceptorWrapperProviders) {
        DevBaseSqlSessionFactories factories = new DefaultDevBaseSqlSessionFactories();
        Map<String, MybatisConfig> mybatisConfigurations = Optional.ofNullable(properties.getConfiguration()).orElse(new HashMap<>(8));
        //处理sql拦截器，使用DevBaseMappersInterceptorProvider中单独包装的内容
        Interceptor[] interceptors = Optional.ofNullable(interceptorWrapperProviders.getIfAvailable()).map(DevBaseMappersInterceptorProvider::supportInterceptor).orElse(new Interceptor[0]);
        devBaseDataSources.getSources().forEach((key, dataSource) -> {
            DevBaseConfiguration configuration = new DevBaseConfiguration(key);
            MybatisConfig config = Optional.ofNullable(mybatisConfigurations.get(key)).orElse(new MybatisConfig());
            Environment environment = new Environment(key, new SpringManagedTransactionFactory(), dataSource);
            configuration.setEnvironment(environment);
            configuration.setMybatisConfig(config);
            if (!ObjectUtils.isEmpty(interceptors)) {
                Stream.of(interceptors).forEach(configuration::addInterceptor);
                logger.info("devbase interceptors has registered {}-configuration: [{}]", key, Arrays.stream(interceptors).map(item -> item.getClass().getName()).collect(Collectors.joining(",")));
            }
            factories.put(key, new SqlSessionFactoryBuilder().build(configuration));
        });
        logger.info("prepare to initialize default devBaseSqlSessionFactories");
        return factories;
    }

    @Bean
    @ConditionalOnMissingBean
    public DevBaseSqlInjectListener devBaseSqlInjectListener() {
        return new DevBaseSqlInjectListener();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 默认的配置，bean实例使用默认配置
     * 使用默认配置时：请设置 devbase-db.use-default-config=true
     * <p>
     * tip:一般在工程创建时考虑sql注入和参数相关的考量，请根据项目实际情况使用
     */
    @Configuration
    @ConditionalOnProperty(name = "devbase-db.server.use-default-config", matchIfMissing = false)
    @DevBaseMappers(devBaseSqlSessionTemplates = "devBaseSqlSessions", devBaseSqlInjectors = "devBaseSqlInjector", devBaseActionManager = "devBaseActionManager")
    public static class DevBaseMappersImportConfiguration {

        /**
         * 默认的sqlSessionTemplate wrapper
         * 内部包装SqlSessionFactory
         */
        @Bean
        public DevBaseSqlSessions devBaseSqlSessions(DevBaseSqlSessionFactories devBaseSqlSessionFactories) {
            DevBaseSqlSessions devBaseSqlSessions = new DefaultDevBaseSqlSessions();
            devBaseSqlSessionFactories.getFactories().forEach((key, factory) -> {
                devBaseSqlSessions.put(key, new SqlSessionTemplate(factory));
            });
            logger.info("prepare to initialize default devBaseSqlSessions");
            return devBaseSqlSessions;
        }

        /**
         * 默认的Action对象参数管理器
         * 通过对DevBaseActionManager的定义，可以完成:
         * 1.action的校验
         * 2.action增强 ===> 实现通用字段的注入等
         *
         * @return
         */
        @Bean
        public DevBaseActionManager devBaseActionManager() {
            DevBaseActionManager devBaseActionManager = new DevBaseActionManager() {
                @Override
                public void manager(Action action) {
                }
            };
            logger.info("prepare to initialize default devBaseActionManager");
            return devBaseActionManager;
        }

        /**
         * 默认的sql注入实现，仅注入核心元数据查询方式
         * <p>
         * tip:目前只注入了sys_sql_config的元数据查询功能,后续可配置元数据表的增删改功能
         */
        @Bean
        public DevBaseSqlInjector devBaseSqlInjector() {
            DefaultDevBaseSqlInjectorProvider defaultDevBaseSqlInjectorProvider = new DefaultDevBaseSqlInjectorProvider();
            logger.info("prepare to initialize default devBaseSqlInjector");
            return defaultDevBaseSqlInjectorProvider.getDevBaseSqlInjector();
        }

        /**
         * 配置sql的收集器，与DefaultDevBaseSqlInjectorProvider组合使用
         * 如果想自定义其他的注入配置请完全自定义DevBaseMappersImportConfiguration中的Bean
         *
         * @return
         */
        @Bean
        public SysSqlConfigInjectMetaCollector sysSqlConfigInjectMetaCollector() {
            return new SysSqlConfigInjectMetaCollector();
        }

        /**
         * 默认的持久层实例DB外观，用与向外部应用提供devbase的rpc服务
         *
         * @param db
         * @return
         */
        @Bean
        public DevBaseService devBaseService(DB db) {
            SimpleDevBaseService defaultDevBaseService = new SimpleDevBaseService(db);
            logger.info("prepare to initialize default devBaseService");
            return defaultDevBaseService;
        }
    }

    /**
     * pageHelper分页插件配置类
     * <p>
     * tip:
     * 考虑到考虑devBase和其他持久层框架共同使用时，将PageInterceptor直接注入到ioc中会产生不必要的麻烦，在devbase中拦截器使用DevBaseMappersInterceptorProvider进行包装
     */
    @Configuration
    @ConditionalOnClass({PageInterceptor.class})
    @ConditionalOnProperty(name = "devbase-db.server.use-page-helper", matchIfMissing = true)
    public static class DevBaseMappersPageHelperConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public DevBaseMappersInterceptorProvider devBaseMappersPageHelperProvider() {
            return () -> {
                PageInterceptor interceptor = new PageInterceptor();
                Properties properties = new Properties();
                interceptor.setProperties(properties);
                return new Interceptor[]{interceptor};
            };
        }
    }


    /**
     * 函数式接口，仅仅为了包装Interceptor
     */
    @FunctionalInterface
    public interface DevBaseMappersInterceptorProvider {
        /**
         * 提供mybatis拦截器
         *
         * @return
         */
        Interceptor[] supportInterceptor();
    }
}
