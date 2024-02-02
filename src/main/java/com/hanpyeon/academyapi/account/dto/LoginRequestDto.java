package com.hanpyeon.academyapi.account.dto;


import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto (
        @NotBlank
        String userPhoneNumber,
        @NotBlank
        String password
){
}