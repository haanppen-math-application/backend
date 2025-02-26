package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.account.model.AccountGrade;
import com.hanpyeon.academyapi.account.model.AccountName;
import com.hanpyeon.academyapi.account.model.AccountPassword;
import com.hanpyeon.academyapi.account.model.AccountPhoneNumber;
import com.hanpyeon.academyapi.account.model.AccountRole;
import com.hanpyeon.academyapi.account.model.Password;
import com.hanpyeon.academyapi.security.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Range;

@Builder
public record RegisterMemberCommand(
        AccountName name,
        AccountGrade grade,
        AccountPhoneNumber phoneNumber,
        AccountRole role,
        AccountPassword password
) {
}
