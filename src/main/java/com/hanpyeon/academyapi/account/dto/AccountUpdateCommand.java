package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.account.model.AccountGrade;
import com.hanpyeon.academyapi.account.model.AccountName;
import com.hanpyeon.academyapi.account.model.AccountPhoneNumber;
import com.hanpyeon.academyapi.account.model.ResetAccountPassword;
import com.hanpyeon.academyapi.account.service.password.AccountPassword;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AccountUpdateCommand(
        @NotNull
        Long targetMemberId,
        AccountPhoneNumber phoneNumber,
        AccountName name,
        AccountGrade grade,
        ResetAccountPassword resetAccountPassword
) {
}
