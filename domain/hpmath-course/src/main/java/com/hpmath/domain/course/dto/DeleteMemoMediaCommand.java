package com.hpmath.domain.course.dto;

import com.hpmath.common.Role;
import jakarta.validation.constraints.NotNull;

public record DeleteMemoMediaCommand(
        @NotNull
        Long memoMediaId,
        @NotNull
        Long memoId,
        @NotNull
        Long requestMemberId,
        @NotNull
        Role role
) {
    public static DeleteMemoMediaCommand of(final Long memoMediaId, final Long memoId, final Long requestMemberId) {
        return new DeleteMemoMediaCommand(memoMediaId, memoId, requestMemberId);
    }
}
