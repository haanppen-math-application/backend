package com.hanpyeon.academyapi.account.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "로그인 정보")
public record LoginRequestDto(
        @NotBlank @Pattern(regexp = "^[0-9]+$") @Schema(description = "전화번호",example = "01000000000")
        String userPhoneNumber,
        @NotBlank @Schema(description = "비밀번호", example = "0000")
        String password
) {
        @Override
        public String toString() {
                return "LoginRequestDto{" +
                        "userPhoneNumber='" + userPhoneNumber + '\'' +
                        ", password='" + "****" + '\'' +
                        '}';
        }
}