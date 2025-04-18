package com.hpmath.hpmathmediadomain.media.dto;

import com.hpmath.hpmathcore.Role;

public record DeleteMediaCommand(
        String mediaSrc,
        Long memberId,
        Role role
) {
}
