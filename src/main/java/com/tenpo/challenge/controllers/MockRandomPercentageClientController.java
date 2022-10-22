package com.tenpo.challenge.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeoutException;

@RequestMapping(value = "/tenpo/percentage")
@RestController
public class MockRandomPercentageClientController {
    private static final Logger log = LoggerFactory.getLogger(MockRandomPercentageClientController.class);

    @GetMapping
    public Integer getPercentage() throws TimeoutException {
        Double random = Math.random() * (100 - 1 + 1) + 1;
        Integer response = random.intValue();

        if (response < 50) {
            log.info("Event: getPercentage - OK");
            return response;
        } else if (response > 50 && response < 60) {
            log.info("Event: getPercentage - RUNTIME_EXCEPTION");
            throw new RuntimeException();
        } else if (response > 60) {
            log.info("Event: getPercentage - TIMEOU_EXCEPTION");
            throw new TimeoutException();
        }
        return response;
    }
}
