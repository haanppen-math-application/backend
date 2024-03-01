package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.security.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

@Schema(description = "계정 등록")
public record RegisterRequestDto(
        @NotBlank String name,
        @Schema(description = "학년 정보 ( 0 ~ 11 )", example = "11") @Range(min = 0, max = 11) Integer grade,
        @Schema(description = "전화번호", example = "01000000000") @NotBlank @Pattern(regexp = "^[0-9]+$") String phoneNumber,
        @Schema(description = "등록 유형 ( student, teacher 중 택 1 )", example = "student") @NotNull(message = "teacher / student 둘중 하나여야 합니다") Role role,
        String password
) {
    @Override
    public String toString() {
        return "RegisterRequestDto{" +
                "name='" + name + '\'' +
                ", grade=" + grade +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                ", password='" + "****" + '\'' +
                '}';
    }
}
