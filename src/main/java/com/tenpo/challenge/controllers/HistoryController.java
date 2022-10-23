package com.tenpo.challenge.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.tenpo.challenge.exceptions.ValidationException;
import com.tenpo.challenge.models.dtos.HistoryResponseDto;
import com.tenpo.challenge.services.AuditHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/tenpo/history")
@RestController
public class HistoryController {
    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);
    @Autowired
    private AuditHistoryService historyService;

    @GetMapping
    public HistoryResponseDto getHistory(@RequestParam(required = false) String operation, @RequestParam(required = false) Integer page)
            throws ValidationException, JsonProcessingException {
        return historyService.getHistory(operation, page);
    }
}
