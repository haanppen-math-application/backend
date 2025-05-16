package com.hpmath.app.batch.member;

import com.hpmath.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ScheduleConfig {
    private final MemberRepository memberRepository;

    @Bean
    AccountScheduler accountScheduler() {
        return new AccountScheduler(memberRepository);
    }
}
