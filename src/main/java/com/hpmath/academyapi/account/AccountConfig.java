package com.hpmath.academyapi.account;

import com.hpmath.academyapi.account.repository.MemberRepository;
import java.util.Random;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
