package com.tenpo.challenge.exceptions;

import com.tenpo.challenge.statics.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ControllerHandlerException {

    private static final Logger log = LoggerFactory.getLogger(ControllerHandlerException.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError> noHandlerFoundException(HttpServletRequest req, NoHandlerFoundException ex) {
        ApiError apiError = ApiError.builder()
                .error(ErrorCode.ROUTE_NOT_FOUND.getName())
                .message(String.format(ErrorCode.ROUTE_NOT_FOUND.getMessage(), req.getRequestURI()))
                .status(ErrorCode.ROUTE_NOT_FOUND.getHttpStatus().value())
                .build();

        log.error(String.format("Event: %s , %s %s", "noHandlerFoundException", ex.getMessage(), ex));
        log.error(String.format("Event: %s, apierror: %s", "noHandlerFoundException", apiError.toString()));

        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(ValidationException.class)
    public HttpEntity<ApiError> handleValidationException(ValidationException ex) {
        ErrorCode errorCode = ex.getValidationErrorCode();

        ApiError apiError = ApiError.builder()
                .error(errorCode.getName())
                .errorCode(errorCode.getCode())
                .message(ex.getMessage())
                .status(errorCode.getHttpStatus().value())
                .build();

        log.error(String.format("Event: %s , %s %s", "handleValidationException", ex.getMessage(), ex));
        log.error(String.format("Event: %s, apierror: %s", "handleValidationException", apiError));

        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(RandomPercentageClientException.class)
    public HttpEntity<ApiError> handleRandomPercentageClientException(RandomPercentageClientException ex) {
        ErrorCode errorCode = ex.getClientErrorCode();

        ApiError apiError = ApiError.builder()
                .error(errorCode.getName())
                .errorCode(errorCode.getCode())
                .message(ex.getMessage())
                .status(errorCode.getHttpStatus().value())
                .build();

        log.error(String.format("Event: %s , %s %s", "handleRandomPercentageClientException", ex.getMessage(), ex));
        log.error(String.format("Event: %s, apierror: %s", "handleRandomPercentageClientException", apiError));

        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    protected HttpEntity<ApiError> handleUnknownException(Exception ex) {
        ApiError apiError = ApiError.builder()
                .error(ErrorCode.INTERNAL_SERVER_ERROR.getName())
                .message(ex.getMessage())
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus().value())
                .build();

        log.error(String.format("Event: %s , %s %s", "handleRuntimeException", ex.getMessage(), ex));
        log.error(String.format("Event: %s, apierror: %s", "handleValidationException", apiError.toString()));

        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

}
