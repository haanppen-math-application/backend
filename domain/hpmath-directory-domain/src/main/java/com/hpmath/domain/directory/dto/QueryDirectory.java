package com.hpmath.domain.directory.dto;

public record QueryDirectory(
        String path,
        Long requestMemberId
) {
}
