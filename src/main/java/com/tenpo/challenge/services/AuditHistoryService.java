package com.tenpo.challenge.services;

import com.tenpo.challenge.models.dtos.HistoryResponseDto;
import com.tenpo.challenge.models.persistence.AuditHistoryPersistence;
import com.tenpo.challenge.repositories.AuditHistoryPersistenceRepository;
import com.tenpo.challenge.translators.HistoryTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AuditHistoryService {
    private static final Logger log = LoggerFactory.getLogger(AuditHistoryService.class);

    @Value("${history.page-size}")
    private Integer pageSize;

    @Autowired
    private AuditHistoryPersistenceRepository auditHistoryPersistenceRepository;

    @Autowired
    private HistoryTranslator historyTranslator;

    @Async
    public void saveOperation(String operation, String request, String response){
        try
        {
            if(operation != null) {
                auditHistoryPersistenceRepository.save(AuditHistoryPersistence.builder()
                        .operation(operation)
                        .request(request)
                        .response(response)
                        .build());
            }
        }
        catch (Exception e){
            log.error(String.format("Event: saveOperation error saving - Mesagge: %s", e.getMessage()));
        }

    }

    public HistoryResponseDto getHistory(String opertion, Integer page){

        PageRequest pageRequest = PageRequest.of(page == null ? 0 : page, pageSize);

        Page<AuditHistoryPersistence> historySaved = auditHistoryPersistenceRepository.findAllOptionalOperationFilter(opertion, pageRequest);

        return historyTranslator.getResponseDto(historySaved, pageRequest.getPageNumber());
    }
}
