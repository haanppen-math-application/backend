package com.hpmath.domain.course.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UpdateMediaMemoCommand(
        @NotNull
        Long memoId,
        @NotNull
        List<MemoMediaUpdateSequenceCommand> mediaSequences,
        @NotNull
        Long requestMemberId
) {
}
