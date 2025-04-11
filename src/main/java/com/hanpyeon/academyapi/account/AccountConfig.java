package com.hanpyeon.academyapi.account;

import com.hanpyeon.academyapi.account.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class AccountConfig {
    @Bean
    Random random() {
        return new Random();
    }

    @Bean
    TimeProvider timeProvider() {
        return new TimeProvider();
    }

    @Bean
    AccountScheduler accountScheduler(final MemberRepository memberRepository) {
        return new AccountScheduler(memberRepository);
    }
}
