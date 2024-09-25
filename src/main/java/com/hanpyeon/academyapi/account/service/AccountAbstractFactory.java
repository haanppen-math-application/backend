package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.service.password.AccountPassword;
import com.hanpyeon.academyapi.account.service.password.AccountPasswordFactory;
import com.hanpyeon.academyapi.security.PasswordHandler;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountAbstractFactory {
    private final AccountPasswordFactory accountPasswordFactory;

    public AccountGrade getGrade(final Integer grade){
        return AccountGrade.of(grade);
    }

    public AccountPassword getPassword(final String rawPassword) {
        return accountPasswordFactory.createNew(rawPassword);
    }

    public AccountPassword getPasswordFromEntity(final String encryptedPassword) {
        return accountPasswordFactory.createWithEntity(encryptedPassword);
    }

    public AccountName getName(final String name) {
        return AccountName.of(name);
    }

    public AccountPhoneNumber getPhoneNumber(final String phoneNumber) {
        return AccountPhoneNumber.of(phoneNumber);
    }

    public AccountRole getAccountRole(final Role role) {
        return AccountRole.of(role);
    }
}
