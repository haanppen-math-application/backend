package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
class AccountGrade {
    private static final int GRADE_MIN = 0;
    private static final int GRADE_MAX = 11;
    private Integer grade;

    private static void validate(final Integer grade) {
        if (grade < GRADE_MIN || grade > GRADE_MAX) {
            throw new AccountException(ErrorCode.ACCOUNT_POLICY);
        }
    }

    public static AccountGrade of(final Integer grade) {
        validate(grade);
        return new AccountGrade(grade);
    }
}
