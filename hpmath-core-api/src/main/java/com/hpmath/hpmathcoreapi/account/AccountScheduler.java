package com.hpmath.hpmathcoreapi.account;

import com.hpmath.hpmathcoreapi.account.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

@RequiredArgsConstructor
@Slf4j
public class AccountScheduler {
    private final MemberRepository memberRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void resetVerificationStatus() {
        log.info("Reset verification status for members : Started");

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        memberRepository.resetVerificationInfos();
        final long totalTimeMillis = stopWatch.getTotalTimeMillis();

        log.info("Reset verification status for members : Finished with [{}.{}] seconds", totalTimeMillis / 1000, totalTimeMillis % 1000);
    }
}
