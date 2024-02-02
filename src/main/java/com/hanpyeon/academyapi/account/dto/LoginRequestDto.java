package com.hanpyeon.academyapi.account.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequestDto (
        @NotBlank @Pattern(regexp = "^[0-9]+$")
        String userPhoneNumber,
        @NotBlank
        String password
){
}