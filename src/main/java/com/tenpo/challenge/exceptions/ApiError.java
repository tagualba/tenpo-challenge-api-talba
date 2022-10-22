package com.tenpo.challenge.exceptions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonDeserialize(builder = ApiError.ApiErrorBuilder.class)
public class ApiError {

    private String error;

    private int errorCode;

    private String message;

    private Integer status;

}
