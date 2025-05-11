package com.hpmath.domain.member.dto;

import com.hpmath.hpmathcore.Role;
import java.time.LocalDateTime;

public record MemberInfoResult(
        Long id,
        String name,
        String phoneNumber,
        Integer grade,
        Role role,
        LocalDateTime registeredDateTime
) {
}
