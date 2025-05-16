package com.hpmath.domain.directory.dto;

import com.hpmath.hpmathcore.Role;

public record QueryDirectory(
        String path,
        Long requestMemberId,
        Role role
) {
}
