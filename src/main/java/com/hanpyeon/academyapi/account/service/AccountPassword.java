package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.PasswordHandler;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
class AccountPassword {
    private static final int MAX_LENGTH = 20;
    private static final int MIN_LENGTH = 4;

    private String encryptedPassword;


    boolean isMatch(final String rawPassword, final PasswordHandler passwordHandler) {
        return passwordHandler.matches(rawPassword, encryptedPassword);
    }

    private static void validate(final String rawPassword) {
        if (rawPassword.isBlank()) {
            return;
        }
        if (rawPassword.length() > MAX_LENGTH || rawPassword.length() < MIN_LENGTH) {
            throw new AccountException("길이 오류. 길이는 " + MIN_LENGTH + " 와 " + MAX_LENGTH + "사이여아합니다.", ErrorCode.ACCOUNT_POLICY);
        }
    }

    public static AccountPassword of(final String rawPassword, final PasswordHandler passwordHandler) {
        validate(rawPassword);
        return new AccountPassword(passwordHandler.getEncodedPassword(rawPassword));
    }
}
