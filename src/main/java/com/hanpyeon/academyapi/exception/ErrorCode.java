package com.hanpyeon.academyapi.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_MEMBER_TARGET("-101", "질문 대상은 선생님이야 합니다"),
    NO_SUCH_MEMBER("-102", "사용자가 존재하지 않습니다."),
    NO_SUCH_QUESTION("-103", "존재하지 않는 질문 게시글입니다."),


    INVALID_UPLOAD_FILE("-801", ""),
    NOT_SUPPORTED_MEDIA("-802", "지원하지 않는 타입의 이미지 입니다."),
    NO_SUCH_MEDIA("-803", "파일을 찾을 수 없습니다.");
    private final String errorCodeResponse;
    private final String errorDescription;

    ErrorCode(String errorCodeResponse, String errorDescription) {
        this.errorCodeResponse = errorCodeResponse;
        this.errorDescription = errorDescription;
    }
}
