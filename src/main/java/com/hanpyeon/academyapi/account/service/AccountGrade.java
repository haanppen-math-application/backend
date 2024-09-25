package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AccountGrade {
    private static final int GRADE_MIN = 0;
    private static final int GRADE_MAX = 11;
    private final Integer grade;


    private static void validate(final Integer grade) {
        if (grade < GRADE_MIN || grade > GRADE_MAX) {
            throw new AccountException(ErrorCode.ACCOUNT_POLICY);
        }
    }

    static AccountGrade of(final Integer grade) {
        validate(grade);
        return new AccountGrade(grade);
    }
}
