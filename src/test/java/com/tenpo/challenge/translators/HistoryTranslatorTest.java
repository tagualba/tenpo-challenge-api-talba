package com.tenpo.challenge.translators;

import com.tenpo.challenge.models.dtos.HistoryResponseDto;
import com.tenpo.challenge.models.persistence.AuditHistoryPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class HistoryTranslatorTest {

    HistoryTranslator historyTranslator;
    @BeforeEach
    public void init(){
        historyTranslator = new HistoryTranslator();
    }

    @Test
    public void getResponseDtoOk(){
        Pageable pageable = PageRequest.of(0, 5);
        Page<AuditHistoryPersistence> auditHistoryPersistence = new PageImpl<>(List.of(AuditHistoryPersistence.builder().id(1l).request("{request}").response("{response}").operation("operation").build(),
                AuditHistoryPersistence.builder().id(2l).request("{request2}").response("{response2}").operation("operation2").build()), pageable, 0);


        HistoryResponseDto historyResponseDto = historyTranslator.getResponseDto(auditHistoryPersistence, 0);

        assertTrue(historyResponseDto.getPage() == 0);
        assertTrue(historyResponseDto.getTotalPages() == 1);
        assertTrue(historyResponseDto.getHistory().size() == 2);
        assertTrue(historyResponseDto.getHistory().get(0).getId() == 1l);
        assertTrue(historyResponseDto.getHistory().get(0).getRequest() == "{request}");
        assertTrue(historyResponseDto.getHistory().get(0).getResponse() == "{response}");
        assertTrue(historyResponseDto.getHistory().get(0).getOperation() == "operation");
        assertTrue(historyResponseDto.getHistory().get(1).getId() == 2l);
        assertTrue(historyResponseDto.getHistory().get(1).getRequest() == "{request2}");
        assertTrue(historyResponseDto.getHistory().get(1).getResponse() == "{response2}");
        assertTrue(historyResponseDto.getHistory().get(1).getOperation() == "operation2");
    }

    @Test
    public void getResponseDtoEmptyOk(){
        Pageable pageable = PageRequest.of(0, 5);
        Page<AuditHistoryPersistence> auditHistoryPersistence = new PageImpl<>(new ArrayList<>(), pageable, 0);


        HistoryResponseDto historyResponseDto = historyTranslator.getResponseDto(auditHistoryPersistence, 0);

        assertTrue(historyResponseDto.getHistory().size() == 0);
        assertTrue(historyResponseDto.getPage() == 0);
        assertTrue(historyResponseDto.getTotalPages() == 0);
    }

}
