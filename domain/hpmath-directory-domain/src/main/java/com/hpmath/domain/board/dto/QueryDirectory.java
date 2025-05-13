package com.hpmath.domain.board.dto;

public record QueryDirectory(
        String path,
        Long requestMemberId
) {
}
