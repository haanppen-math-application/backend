package com.hpmath.academyapi.account.service;

import com.hpmath.academyapi.account.dto.AccountUpdateCommand;
import com.hpmath.academyapi.account.dto.Password;
import com.hpmath.academyapi.account.dto.StudentUpdateCommand;
import com.hpmath.academyapi.account.dto.UpdateTeacherCommand;
import com.hpmath.academyapi.account.entity.Member;
import com.hpmath.academyapi.account.exceptions.AccountException;
import com.hpmath.academyapi.account.exceptions.NoSuchMemberException;
import com.hpmath.academyapi.account.repository.MemberRepository;
import com.hpmath.academyapi.account.service.policy.AccountPolicyManager;
import com.hpmath.academyapi.exception.ErrorCode;
import com.hpmath.academyapi.security.PasswordHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class AccountUpdateService {
    private final MemberRepository memberRepository;

    private final AccountPolicyManager accountPolicyManager;
    private final PasswordHandler passwordHandler;

    public void updateMember(final AccountUpdateCommand updateCommand) {
        final Member member = loadMember(updateCommand.targetMemberId());

        updatePhoneNumber(updateCommand.phoneNumber(), member);
        updateName(updateCommand.name(), member);
        updatePassword(updateCommand.prevPassword(), updateCommand.newPassword(), member);

        accountPolicyManager.checkPolicy(member);
    }

    public void updateMember(final StudentUpdateCommand updateCommand) {
        final Member member = loadMember(updateCommand.memberId());

        updatePhoneNumber(updateCommand.phoneNumber(), member);
        updateGrade(updateCommand.grade(), member);
        updateName(updateCommand.name(), member);

        accountPolicyManager.checkPolicy(member);
    }

    public void updateMember(final UpdateTeacherCommand updateCommand) {
        final Member member = loadMember(updateCommand.memberId());

        updatePhoneNumber(updateCommand.phoneNumber(), member);
        updateName(updateCommand.name(), member);

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
