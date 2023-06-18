package com.project.smartcharge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication()
@EnableAsync
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class SmartchargeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartchargeApplication.class, args);
    }

}
