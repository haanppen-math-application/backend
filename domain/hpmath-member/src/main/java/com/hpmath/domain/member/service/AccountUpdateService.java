package com.hpmath.domain.member.service;

import com.hpmath.domain.member.Member;
import com.hpmath.domain.member.MemberRepository;
import com.hpmath.domain.member.dto.AccountUpdateCommand;
import com.hpmath.domain.member.dto.Password;
import com.hpmath.domain.member.dto.StudentUpdateCommand;
import com.hpmath.domain.member.dto.UpdateTeacherCommand;
import com.hpmath.domain.member.exceptions.AccountException;
import com.hpmath.domain.member.exceptions.NoSuchMemberException;
import com.hpmath.domain.member.service.policy.AccountPolicyManager;
import com.hpmath.common.ErrorCode;
import com.hpmath.common.web.PasswordHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.REPEATABLE_READ)
@CacheConfig(cacheNames = "hpmath::member::info")
public class AccountUpdateService {
    private final MemberRepository memberRepository;

    private final AccountPolicyManager accountPolicyManager;
    private final PasswordHandler passwordHandler;

    @CacheEvict(key = "#command.targetMemberId()")
    public void updateMember(final AccountUpdateCommand command) {
        final Member member = loadMember(command.targetMemberId());

        updatePhoneNumber(command.phoneNumber(), member);
        updateName(command.name(), member);
        updatePassword(command.prevPassword(), command.newPassword(), member);

        accountPolicyManager.checkPolicy(member);
    }

    @CacheEvict(key = "#command.memberId()")
    public void updateMember(final StudentUpdateCommand command) {
        final Member member = loadMember(command.memberId());

        updatePhoneNumber(command.phoneNumber(), member);
        updateGrade(command.grade(), member);
        updateName(command.name(), member);

        accountPolicyManager.checkPolicy(member);
    }

    @CacheEvict(key = "#command.memberId()")
    public void updateMember(final UpdateTeacherCommand command) {
        final Member member = loadMember(command.memberId());

        updatePhoneNumber(command.phoneNumber(), member);
        updateName(command.name(), member);

        accountPolicyManager.checkPolicy(member);
    }

    /**
     * password가 passwordHandler 의존하도록 구성
     *
     * @param prevPassword 이전 비밀번호
     * @param newPassword  새로 설정할 비밀번호
     * @param account      수정할 계정
     */
    private void updatePassword(final Password prevPassword, final Password newPassword, final Member account) {
        if (prevPassword.isMatch(account.getPassword(), passwordHandler)) {
            account.setPassword(newPassword.getEncryptedPassword(passwordHandler));
            return;
        }
        throw new AccountException("잘못된 비밀번호 입니다.", ErrorCode.ACCOUNT_POLICY);
    }

    private void updateName(final String accountName, final Member targetAccount) {
        targetAccount.setName(accountName);
    }

    private void updateGrade(final Integer accountGrade, final Member targetAccount) {
        targetAccount.setGrade(accountGrade);
    }

    private void updatePhoneNumber(final String accountPhoneNumber, final Member targetAccount) {
        targetAccount.setPhoneNumber(accountPhoneNumber);
    }

    private Member loadMember(final Long targetMemberId) {
        return memberRepository.findMemberByIdAndRemovedIsFalse(targetMemberId)
                .orElseThrow(() -> new NoSuchMemberException("찾을 수 없습니다.", ErrorCode.NO_SUCH_MEMBER));
    }
}
