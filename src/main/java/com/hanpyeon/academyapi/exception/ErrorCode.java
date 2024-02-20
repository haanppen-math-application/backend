package com.hanpyeon.academyapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    AUTHENTICATION_FAILED_EXCEPTION(HttpStatus.UNAUTHORIZED, "-001", "인증 불가"),
    DENIED_EXCEPTION(HttpStatus.FORBIDDEN, "-002", "적절한 권한 부재"),

    METHOD_ARGUMENT_NOT_VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "-101", "잘못된 JSON 필드"),
    HTTP_MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, "-102", "부적절한 API 요청 형식"),

    ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "-201", "이미 등록된 사용자"),
    ILLEGAL_MEMBER_REGISTER_FORMAT(HttpStatus.BAD_REQUEST, "-202", "해당 유형 사용자 등록 필수값 부재"),
    NOT_REGISTERED_MEMBER(HttpStatus.BAD_REQUEST, "-203", "등록되지 않은 사용자"),
    NOT_SUPPORTED_MEMBER_TYPE(HttpStatus.BAD_REQUEST, "-204", "등록 할 수 없는 사용자 유형"),

    INVALID_MEMBER_TARGET(HttpStatus.BAD_REQUEST, "-101", "질문 게시글 대상은 선생님"),
    NO_SUCH_MEMBER(HttpStatus.BAD_REQUEST, "-102", "존재하지 않는 사용자"),
    NO_SUCH_QUESTION(HttpStatus.BAD_REQUEST, "-103", "존재하지 않는 질문 게시글"),


    INVALID_UPLOAD_FILE(HttpStatus.BAD_REQUEST, "-801", "파일 검증 실패"),
    NOT_SUPPORTED_MEDIA(HttpStatus.BAD_REQUEST, "-802", "지원하지 않는 타입의 파일"),
    NO_SUCH_MEDIA(HttpStatus.BAD_REQUEST, "-803", "파일 찾기 실패"),
    MEDIA_STORE_EXCEPTION(HttpStatus.BAD_REQUEST, "-804", "파일을 저장 불가");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorDescription;

    ErrorCode(HttpStatus httpStatus, String errorCodeResponse, String errorDescription) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCodeResponse;
        this.errorDescription = errorDescription;
    }
}
