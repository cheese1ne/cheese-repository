package com.cheese.db.sample;

import com.cheese.db.autoconfigure.EnableDevBase;
import com.cheese.db.core.condition.Actions;
import com.cheese.db.core.condition.load.LoadAction;
import com.cheese.db.core.mapper.DB;
import com.cheese.db.core.support.DevBaseConstant;
import com.cheese.db.sample.service.ICommonService;
import com.cheese.db.spring.injector.event.InjectType;
import com.cheese.db.spring.injector.event.SqlInjectEvent;
import com.cheese.db.spring.injector.metadata.InjectMeta;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.List;

/**
 * devbase功能可以兼容DataSourceAutoConfiguration的功能，进行相应的配置即可
 *
 * @author sobann
 */
@EnableDevBase
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CheeseSampleApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CheeseSampleApplication.class, args);
    }

    @Resource
    private DB db;
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private ICommonService commonService;

    @Override
    public void run(String... args) throws Exception {
        LoadAction action = Actions.getLoad(DevBaseConstant.DEFAULT_SQL_CONFIG_DATASOURCE, DevBaseConstant.DEFAULT_SQL_CONFIG_CODE);
        List<InjectMeta> injectMetas = db.doActionGetList(action, InjectMeta.class);
        //默认注入到DB.class中,可根据项目实际持久层mapper实现
        applicationContext.publishEvent(new SqlInjectEvent(this, DB.class, InjectType.ALL, injectMetas));

        //事务测试
        commonService.useTransaction();
    }
}
