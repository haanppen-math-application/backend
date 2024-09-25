package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.account.service.password.AccountPassword;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Account {
    private final Long id;
    private AccountPhoneNumber phoneNumber;
    private AccountName accountName;
    private AccountRole accountRole;
    private AccountGrade grade;
    private AccountPassword password;

    public void updateAccountName(AccountName accountName) {
        this.accountName = accountName;
    }

    public void updateGrade(AccountGrade grade) {
        this.grade = grade;
    }

    public void changePassword(final String rawPassword, final AccountPassword accountNewPassword) {
        if (this.password.isMatch(rawPassword)) {
            this.password = accountNewPassword;
            return;
        }
        throw new AccountException(ErrorCode.INVALID_PASSWORD_EXCEPTION);
    }

    public void setPhoneNumber(AccountPhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static Account of(final AccountPhoneNumber phoneNumber, final AccountName name, final AccountRole role, final AccountGrade grade, final AccountPassword password) {
        return new Account(null, phoneNumber, name, role, grade, password);
    }
    public static Account of(final Long accountId, final AccountPhoneNumber phoneNumber, final AccountName name, final AccountRole role, final AccountGrade grade, final AccountPassword password) {
        return new Account(accountId, phoneNumber, name, role, grade, password);
    }
}
