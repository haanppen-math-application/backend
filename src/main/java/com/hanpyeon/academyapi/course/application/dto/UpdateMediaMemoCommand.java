package com.hanpyeon.academyapi.course.application.dto;

import jakarta.annotation.Nonnull;

import java.util.List;

public record UpdateMediaMemoCommand(
        @Nonnull Long memoId,
        @Nonnull List<MemoMediaSequenceModifyCommand> mediaSequences,
        @Nonnull Long requestMemberId
) {
}
