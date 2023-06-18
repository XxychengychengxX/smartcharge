/**
 * @author Valar Morghulis
 * @Date 2023/5/30
 */
package com.project.smartcharge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class MyThreadPoolConfig {

    @Bean("myThreadPool")
    public ExecutorService myThreadPool() {
        ExecutorService executorService = Executors.newFixedThreadPool(7);

        return executorService;
    }

    @Bean
    public BlockingDeque<Integer> lowModeBlockingDeque(){
        return new LinkedBlockingDeque<Integer> (6);
    }
    @Bean
    public BlockingDeque<Integer> fastModeBlockingDeque(){
        return new LinkedBlockingDeque<Integer> (6);
    }
}
