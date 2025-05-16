package com.hpmath.domain.media.dto;

import com.hpmath.common.Role;

public record DeleteMediaCommand(
        String mediaSrc,
        Long memberId,
        Role role
) {
}
