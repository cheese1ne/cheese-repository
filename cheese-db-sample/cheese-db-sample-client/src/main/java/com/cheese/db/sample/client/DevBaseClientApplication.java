package com.cheese.db.sample.client;

import com.cheese.db.common.condition.Action;
import com.cheese.db.common.condition.Actions;
import com.cheese.db.common.condition.load.LoadAction;
import com.cheese.db.common.service.DevBaseService;
import com.cheese.db.common.service.DevBaseServiceProvider;
import com.cheese.db.rpc.DevBaseRpcAutoImportSelector;
import com.cheese.db.rpc.serializer.hessian.HessianSerializer;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Import;

import java.util.Map;

/**
 * DevBase服务消费者
 *
 * @author sobann
 */
@SpringBootApplication
@Import(DevBaseRpcAutoImportSelector.class)
public class DevBaseClientApplication implements CommandLineRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(DevBaseClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        DevBaseServiceProvider serviceProvider = applicationContext.getBean(DevBaseServiceProvider.class);
        DevBaseService devBaseService = serviceProvider.getDevBaseService();

        // rpc远程调用参数序列化后再接收
        LoadAction loadAction = Actions.getLoad("bus", "LOAD_BORROW_APPLY_INFO");
        loadAction.putParam("apply_id", 1);
        loadAction.putParam("is_deleted", 0);
        Map<String, Object> taskInfo = devBaseService.doActionGetOne(loadAction);

        // 数据传输过去用字节传输，传输完成后再进行反序列化为对象
        HessianSerializer hessianSerializer = new HessianSerializer();
        byte[] serialize = hessianSerializer.serialize((Action) loadAction);
        System.out.println("applyInfo = " + taskInfo);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
