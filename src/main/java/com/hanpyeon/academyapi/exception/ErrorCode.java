package com.hanpyeon.academyapi.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    ALREADY_REGISTERED("-201", "이미 등록된 사용자 입니다"),
    ILLEGAL_MEMBER_REGISTER_FORMAT("-202", "해당 유형의 사용자를 등록하기 위한 필수값이 없습니다"),
    NOT_REGISTERED_MEMBER("-203", "등록되지 않은 사용자입니다"),
    NOT_SUPPORTED_MEMBER_TYPE("-204", "등록 할 수 없는 사용자 유형입니다."),

    INVALID_MEMBER_TARGET("-101", "질문 대상은 선생님이야 합니다"),
    NO_SUCH_MEMBER("-102", "사용자가 존재하지 않습니다."),
    NO_SUCH_QUESTION("-103", "존재하지 않는 질문 게시글입니다."),


    INVALID_UPLOAD_FILE("-801", ""),
    NOT_SUPPORTED_MEDIA("-802", "지원하지 않는 타입의 이미지 입니다."),
    NO_SUCH_MEDIA("-803", "파일을 찾을 수 없습니다."),
    MEDIA_STORE_EXCEPTION("-804", "파일을 저장할 수 없습니다.");
    private final String errorCodeResponse;
    private final String errorDescription;

    ErrorCode(String errorCodeResponse, String errorDescription) {
        this.errorCodeResponse = errorCodeResponse;
        this.errorDescription = errorDescription;
    }
}
