package com.hanpyeon.academyapi.account.dto;


import com.hanpyeon.academyapi.account.model.Password;
import com.hanpyeon.academyapi.account.validation.PhoneNumberConstraint;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@Schema(description = "로그인 정보")
public record LoginRequestDto(
        @Schema(description = "전화번호", example = "01000000000")
        @PhoneNumberConstraint
        String userPhoneNumber,
        @Valid
        Password password
) {
}