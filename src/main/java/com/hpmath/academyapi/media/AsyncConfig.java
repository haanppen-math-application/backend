package com.hpmath.academyapi.media;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {
    @Bean(name = "mediaAsyncExecutor")
    public Executor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("mediaExecutor-");
        executor.setCorePoolSize(10); // 기본 스레드 수
        executor.setMaxPoolSize(30);  // 최대 스레드 수
        executor.setQueueCapacity(100); // 큐 용량
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAllowCoreThreadTimeOut(true);
        executor.setAwaitTerminationSeconds(3);
        executor.setRejectedExecutionHandler(new CallerRunsPolicy());

        executor.initialize();
        return executor;
    }
}
