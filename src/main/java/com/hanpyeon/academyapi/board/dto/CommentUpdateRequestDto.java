package com.hanpyeon.academyapi.board.dto;

public record CommentUpdateRequestDto(
        String content
) {
    @Override
    public String toString() {
        return "CommentUpdateRequestDto{" +
                "content='" + content + '\'' +
                '}';
    }
}
