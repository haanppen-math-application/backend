package com.hanpyeon.academyapi.account.controller;

import com.hanpyeon.academyapi.account.service.Password;
import com.hanpyeon.academyapi.account.validation.PhoneNumberConstraint;
import jakarta.validation.Valid;

class Responses {
    record ChangedPasswordResponse(
            @PhoneNumberConstraint
            String phoneNumber,
            @Valid
            Password changedPassword
    ) {
    }
}
