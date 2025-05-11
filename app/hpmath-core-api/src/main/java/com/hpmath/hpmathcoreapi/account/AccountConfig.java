package com.hpmath.hpmathcoreapi.account;

import com.hpmath.hpmathcoreapi.account.repository.MemberRepository;
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
    AccountScheduler accountScheduler(final MemberRepository memberRepository) {
        return new AccountScheduler(memberRepository);
    }
}
