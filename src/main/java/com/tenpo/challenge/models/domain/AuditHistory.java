package com.tenpo.challenge.models.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuditHistory {

    private Long id;

    private String operation;

    private String request;

    private String response;

}
