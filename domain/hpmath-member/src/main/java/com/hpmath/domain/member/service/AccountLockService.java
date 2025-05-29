package com.hpmath.domain.member.service;

import com.hpmath.domain.member.Member;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountLockService {
    private final Integer maxTryCount;
    private final Long lockTimeMinutes;

    public AccountLockService(
            @Value("${login.lock.maxTryCount}") Integer maxTryCount,
            @Value("${login.lock.minutes}") final Long lockTimeMinutes
    ) {
        this.maxTryCount = maxTryCount;
        this.lockTimeMinutes = lockTimeMinutes;
    }

    /**
     * 로그인 실패 시, 호출해야하는 메소드입니다.
     *
     * @param member     로그인 하고자 하는 계정
     * @param currentTime 로그인 시도한 시간.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateLoginFailedInfo(final Member member, final LocalDateTime currentTime) {

        member.increaseLoginTryCount();
        log.info("account {}, Increase login try count to {}", member.getId(), member.getLoginTryCount());

        if (member.isOverMaxLoginTryCount(this.maxTryCount)) {
            member.lock(currentTime);
            log.info("Lock account {}, lock started at {}", member.getId(), currentTime);
        }
    }

    /**
     * 해당 계정에 로그인 할 수 있는지 판단하는 메소드 입니다.
     *
     * @param member 로그인 하고자 하는 대상입니다.
     * @param currentTime 로그인 시도 시간
     * @return 로그인을 시도할 수 있다면 true, 아니라면 false
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean checkAllowedToLogin(final Member member, final LocalDateTime currentTime) {
        return member.canLoginAt(currentTime, this.lockTimeMinutes);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void unlockWhenLocked(final Member member) {
        if (member.getLocked()) {
            unlock(member);
        }
    }

    private void unlock(final Member member) {
        member.unlock();
        log.info("member unlocked: {}", member.getId());
    }
}
