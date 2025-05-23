package com.hpmath.domain.board.read;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class BoardReadConfig {
    @Bean
    public ExecutorService workers() {;
        return Executors.newFixedThreadPool(100);
    }
}
