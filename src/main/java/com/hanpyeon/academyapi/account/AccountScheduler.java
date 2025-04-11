package com.hanpyeon.academyapi.account;

import com.hanpyeon.academyapi.account.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
public class AccountScheduler {
    private final MemberRepository memberRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void resetVerificationStatus() {
        log.info("Reset verification status for members : Started");
        memberRepository.resetVerificationInfos();
        log.info("Reset verification status for members : Finished");
    }
}
