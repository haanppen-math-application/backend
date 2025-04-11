package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.ChangedPassword;
import com.hanpyeon.academyapi.account.dto.Password;
import com.hanpyeon.academyapi.account.dto.VerifyAccountCodeCommand;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.sms.Message;
import com.hanpyeon.academyapi.sms.MessageSender;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.PasswordHandler;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountPasswordRefreshService {
    private final PasswordHandler passwordHandler;
    private int maxVerifyMessageSendCount = 3;
    private int maxVerifyErrorCount = 3;
    private int validMinute = 5;
    private final Random random;
    private final MessageSender messageSender;
    private final MemberRepository memberRepository;

    @Transactional
    public void generateVerificationCode(final String phoneNumber) {
        final Member member = memberRepository.findMemberByPhoneNumberAndRemovedIsFalse(phoneNumber)
                .orElseThrow(() -> new AccountException(ErrorCode.NO_SUCH_MEMBER));
        if (member.getVerifyMessageSendCount() > maxVerifyMessageSendCount) {
            throw new AccountException(maxVerifyMessageSendCount + "더 이상 보낼 수 없습니다. 관리자에게 초기화를 요청해주세요", ErrorCode.ACCOUNT_VERIFICATION_EXCEPTION);
        }
        final String verificationCode = generateVerificationCode();
        member.setVerificationCode(verificationCode);

        messageSender.sendMessage(new Message(member.getPhoneNumber(),
                "인증번호 : [" + verificationCode + "]\n"
                        + " 현재 시도 횟수 : " + member.getLoginTryCount() + "/" + maxVerifyMessageSendCount + "\n"
                        + " 남은 입력 시간 : " + validMinute + "\n")
        );
    }

    @Transactional
    public ChangedPassword verifyCode(final VerifyAccountCodeCommand verifyAccountCode) {
        final Member member = memberRepository.findMemberByPhoneNumberAndRemovedIsFalse(verifyAccountCode.phoneNumber())
                .orElseThrow(() -> new AccountException(ErrorCode.NO_SUCH_MEMBER));
        validateCode(member, verifyAccountCode.verificationCode());
        member.resetVerifyInfo();

        final Password changedPassword = setNewPassword(member.getPhoneNumber());
        return new ChangedPassword(member.getPhoneNumber(), changedPassword);
    }

    private Password setNewPassword(final String phoneNumber) {
        final Password newPassword = generatePassword();
        final Member member = memberRepository.findMemberByPhoneNumberAndRemovedIsFalse(phoneNumber)
                .orElseThrow();
        member.setPassword(newPassword.getEncryptedPassword(passwordHandler));
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

    private Password generatePassword() {
        final StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            password.append(random.nextInt(10));
        }
        return new Password(password.toString());
    }

    private String generateVerificationCode() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            password.append(random.nextInt(10));
        }
        return password.toString();
    }
}
