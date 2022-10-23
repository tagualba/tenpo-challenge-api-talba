package com.tenpo.challenge.services;

import com.tenpo.challenge.models.domain.AuditHistory;
import com.tenpo.challenge.models.dtos.HistoryResponseDto;
import com.tenpo.challenge.models.persistence.AuditHistoryPersistence;
import com.tenpo.challenge.repositories.AuditHistoryPersistenceRepository;
import com.tenpo.challenge.translators.HistoryTranslator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuditHistoryServiceTest {

    @Mock
    private AuditHistoryPersistenceRepository auditHistoryPersistenceRepository;

    @Mock
    private HistoryTranslator historyTranslator;

    @InjectMocks
    private AuditHistoryService auditHistoryService;

    @Test
    public void saveOperationOk() {
        when(auditHistoryPersistenceRepository.save(any())).thenReturn(any());

        auditHistoryService.saveOperation("Operation", "{request}", "{response}");
    }

    @Test
    public void saveOperationRepositoryErrorOk() {
        when(auditHistoryPersistenceRepository.save(any())).thenThrow(new RuntimeException());

        auditHistoryService.saveOperation("Operation", "{request}", "{response}");
    }

    @Test
    public void getHistoryOk() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<AuditHistoryPersistence> auditHistoryPersistence = new PageImpl<>(List.of(AuditHistoryPersistence.builder().id(1l).request("{request}").response("{response}").operation("operation").build(),
                AuditHistoryPersistence.builder().id(2l).request("{request2}").response("{response2}").operation("operation2").build()), pageable, 0);
        HistoryResponseDto historyResponseDto = HistoryResponseDto.builder()
                .totalPages(1)
                .page(0)
                .history(
                        List.of(AuditHistory.builder()
                                        .id(1l)
                                        .request("{request}")
                                        .response("{response}")
                                        .operation("operation")
                                        .build(),
                                AuditHistory.builder()
                                        .id(2l)
                                        .request("{request2}")
                                        .response("{response2}")
                                        .operation("operation")
                                        .build())).build();

        when(auditHistoryPersistenceRepository.findAllOptionalOperationFilter(any(), any())).thenReturn(auditHistoryPersistence);
        when(historyTranslator.getResponseDto(any(), any())).thenReturn(historyResponseDto);

        HistoryResponseDto history = auditHistoryService.getHistory(null, null);

        assertTrue(history.getPage() == historyResponseDto.getPage());
        assertTrue(history.getTotalPages() == historyResponseDto.getTotalPages());
        assertTrue(history.getHistory().size() == historyResponseDto.getHistory().size());
    }

    @Test
    public void getOperationRepositoryErrorOk() {
        when(auditHistoryPersistenceRepository.findAllOptionalOperationFilter(any(),any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> auditHistoryService.getHistory(null, null));
    }
}
