package com.tenpo.challenge.services;

import com.tenpo.challenge.exceptions.ControllerHandlerException;
import com.tenpo.challenge.models.persistence.AuditHistoryPersistence;
import com.tenpo.challenge.repositories.AuditHistoryPersistenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AuditHistoryService {
    private static final Logger log = LoggerFactory.getLogger(AuditHistoryService.class);


    @Autowired
    private AuditHistoryPersistenceRepository auditHistoryPersistenceRepository;

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
}
