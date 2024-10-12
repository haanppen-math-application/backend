package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.SendPasswordCommand;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.account.service.password.AccountPassword;
import com.hanpyeon.academyapi.account.service.password.AccountPasswordFactory;
import com.hanpyeon.academyapi.account.service.sms.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountPasswordRefreshService {
    private final Random random;
    private final MessageSender messageSender;
    private final MemberRepository memberRepository;
    private final AccountPasswordFactory accountPasswordFactory;

    @Transactional
    public void setNewPassword(final AccountPhoneNumber accountPhoneNumber) {
        final String newPassword = generatePassword();
        final AccountPassword accountPassword = accountPasswordFactory.createNew(newPassword);
        final Member member = memberRepository.findMemberByPhoneNumberAndRemovedIsFalse(accountPhoneNumber.getPhoneNumber())
                .orElseThrow();
        member.setPassword(accountPassword.getEncryptedPassword());
        messageSender.sendNewPassword(new SendPasswordCommand(accountPhoneNumber, newPassword));
    }


    private String generatePassword() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            password.append(random.nextInt(10));
        }
        return password.toString();
    }
}
