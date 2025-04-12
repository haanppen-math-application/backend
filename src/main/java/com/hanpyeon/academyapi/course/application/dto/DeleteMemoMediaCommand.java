package com.hanpyeon.academyapi.course.application.dto;

public record DeleteMemoMediaCommand(
        Long memoMediaId,
        Long memoId,
        Long requestMemberId
) {
    public static DeleteMemoMediaCommand of(final Long memoMediaId, final Long memoId, final Long requestMemberId) {
        return new DeleteMemoMediaCommand(memoMediaId, memoId, requestMemberId);
    }
}
