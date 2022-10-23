package com.tenpo.challenge.models.dtos;

import com.tenpo.challenge.models.domain.AuditHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryResponseDto {

    private List<AuditHistory> history;

    private Integer page;

    private Integer totalPages;

}
