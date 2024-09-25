package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.security.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.awt.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {
    private final Long id;
    private final AccountPhoneNumber phoneNumber;
    private final AccountName accountName;
    private final AccountRole accountRole;
    private final AccountGrade grade;
    private final AccountPassword password;

    public String getPhoneNumber() {
        return this.phoneNumber.getPhoneNumber();
    }

    public String getName() {
        return this.accountName.getName();
    }

    public int getGrade() {
        return this.grade.getGrade();
    }

    public Role getRole() {
        return this.accountRole.getRole();
    }

    public String getEncryptedPassword() {
        return this.password.getEncryptedPassword();
    }

    static Account of(final AccountPhoneNumber phoneNumber, final AccountName name, final AccountRole role, final AccountGrade grade, final AccountPassword password) {
        return new Account(null, phoneNumber, name, role, grade, password);
    }
}
