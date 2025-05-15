package com.hpmath.app.board.view.batch;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class BoardViewBackUpConfig {
    @Bean
    ScheduledExecutorService threadPoolExecutor() {
        return new ScheduledThreadPoolExecutor(3);
    }
}
