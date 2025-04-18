package com.hpmath.hpmathwebcommon.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hpmath.hpmathcore.ErrorCode;
import java.util.List;
import lombok.Getter;

@Getter
@JsonPropertyOrder(value = {"errorCode"})
public class ExceptionResponseBody {
    private final String errorCode;
    private final String errorDescription;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("details")
    private final String detailMessages;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<String> errors;

    private ExceptionResponseBody(String errorCode, String errorDescription, String detailMessages, List<String> errors) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.detailMessages = detailMessages;
        this.errors = errors;
    }

    public static ExceptionResponseBody of(ErrorCode errorCode) {
        return new ExceptionResponseBody(errorCode.getErrorCode(), errorCode.getErrorDescription(), null, null);
    }

    public static ExceptionResponseBody of(ErrorCode errorCode, String detailMessages) {
        return new ExceptionResponseBody(errorCode.getErrorCode(), errorCode.getErrorDescription(), detailMessages, null);
    }

    public static ExceptionResponseBody of(ErrorCode errorCode, List<String> errors) {
        return new ExceptionResponseBody(errorCode.getErrorCode(), errorCode.getErrorDescription(), null, errors);
    }

    public static ExceptionResponseBody of(ErrorCode errorCode, String detailMessages, List<String> errors) {
        return new ExceptionResponseBody(errorCode.getErrorCode(), errorCode.getErrorDescription(), detailMessages, errors);
    }
}
