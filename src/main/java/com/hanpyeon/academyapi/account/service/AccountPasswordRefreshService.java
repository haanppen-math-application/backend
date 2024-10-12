package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.ChangedPassword;
import com.hanpyeon.academyapi.account.dto.SendValidationCodeCommand;
import com.hanpyeon.academyapi.account.dto.VerifyAccountCode;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.account.service.password.AccountPassword;
import com.hanpyeon.academyapi.account.service.password.AccountPasswordFactory;
import com.hanpyeon.academyapi.account.service.sms.MessageSender;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountPasswordRefreshService {
    private int maxVerifyMessageSendCount = 3;
    private int maxVerifyErrorCount = 3;
    private int validMinute = 5;
    private final Random random;
    private final MessageSender messageSender;
    private final MemberRepository memberRepository;
    private final AccountPasswordFactory accountPasswordFactory;

    @Transactional
    public void generateVerificationCode(final String phoneNumber) {
        final Member member = memberRepository.findMemberByPhoneNumberAndRemovedIsFalse(phoneNumber)
                .orElseThrow(() -> new AccountException(ErrorCode.NO_SUCH_MEMBER));
        if (member.getVerifyMessageSendCount() > maxVerifyMessageSendCount) {
            throw new AccountException(maxVerifyMessageSendCount + "더 이상 보낼 수 없습니다. 관리자에게 초기화를 요청해주세요", ErrorCode.ACCOUNT_VERIFICATION_EXCEPTION);
        }
        final String verificationCode = generateVerificationCode();
        member.setVerificationCode(verificationCode);
        messageSender.sendValidationCode(new SendValidationCodeCommand(AccountPhoneNumber.of(member.getPhoneNumber()), verificationCode, member.getVerifyMessageSendCount(), maxVerifyMessageSendCount, validMinute));
    }

    @Transactional
    public ChangedPassword verifyCode(final VerifyAccountCode verifyAccountCode) {
        final Member member = memberRepository.findMemberByPhoneNumberAndRemovedIsFalse(verifyAccountCode.phoneNumber())
                .orElseThrow(() -> new AccountException(ErrorCode.NO_SUCH_MEMBER));
        validateCode(member, verifyAccountCode.verificationCode());
        member.resetVerifyInfo();
        final AccountPhoneNumber accountPhoneNumber = AccountPhoneNumber.of(member.getPhoneNumber());
        final String changedPassword = setNewPassword(accountPhoneNumber);
        return new ChangedPassword(verifyAccountCode.phoneNumber(), changedPassword);
    }

    private String setNewPassword(final AccountPhoneNumber accountPhoneNumber) {
        final String newPassword = generatePassword();
        final AccountPassword accountPassword = accountPasswordFactory.createNew(newPassword);
        final Member member = memberRepository.findMemberByPhoneNumberAndRemovedIsFalse(accountPhoneNumber.getPhoneNumber())
                .orElseThrow();
        member.setPassword(accountPassword.getEncryptedPassword());
        return newPassword;
    }

    private void validateCode(final Member member, final String verificationCode) {
        if (Duration.between(member.getVerifyDateTime(), LocalDateTime.now()).toMinutes() > validMinute) {
            throw new AccountException("인증시간 초과", ErrorCode.ACCOUNT_VERIFICATION_EXCEPTION);
        }
        if (!member.getVerificationCode().equals(verificationCode)) {
            throw new AccountException("인증 코드가 다릅니다.", ErrorCode.ACCOUNT_VERIFICATION_EXCEPTION);
        }
    }

    private String generatePassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            password.append(random.nextInt(10));
        }
        return password.toString();
    }

    private String generateVerificationCode() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            password.append(random.nextInt(10));
        }
        return password.toString();
    }
}
