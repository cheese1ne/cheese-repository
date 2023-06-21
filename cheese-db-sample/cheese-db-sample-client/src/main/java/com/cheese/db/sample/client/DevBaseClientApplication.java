package com.cheese.db.sample.client;

import com.cheese.db.client.EnableDevBaseClient;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * DevBase服务消费者
 *
 * @author sobann
 */
@EnableDevBaseClient
@SpringBootApplication
public class DevBaseClientApplication implements CommandLineRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(DevBaseClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
