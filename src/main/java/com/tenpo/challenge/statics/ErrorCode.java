package com.tenpo.challenge.statics;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    INVALID_DATA(1001, "Invalid data input", HttpStatus.BAD_REQUEST),
    REPEAT_EMAIL(1002, "Repeat Email", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1003, "Invalid Token Access", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN(1004, "Token Expired ", HttpStatus.UNAUTHORIZED),
    COMBINATION_FAIL(1005, "Invalid Combination", HttpStatus.NOT_FOUND),

    EXTERNAL_CLIENT_ERROR(4001, "External services fail", HttpStatus.INTERNAL_SERVER_ERROR),
    ROUTE_NOT_FOUND(4004, "Route Not Found", HttpStatus.NOT_FOUND),

    INTERNAL_SERVER_ERROR(5000, "Internal error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final Integer code;

    private final String message;

    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getName() {
        return this.name();
    }

}
