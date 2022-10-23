package com.tenpo.challenge.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tenpo.challenge.exceptions.ControllerHandlerException;
import com.tenpo.challenge.exceptions.RandomPercentageClientException;
import com.tenpo.challenge.exceptions.ValidationException;
import com.tenpo.challenge.services.CalculateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/tenpo/calculate")
@RestController
public class CalculateController {

    private static final Logger log = LoggerFactory.getLogger(ControllerHandlerException.class);

    @Autowired
    private CalculateService calulateServices;

    @GetMapping
    public String calculate(@RequestParam(required = false) Double valueA, @RequestParam(required = false) Double valueB)
            throws ValidationException, RandomPercentageClientException, JsonProcessingException {
        return calulateServices.calculate(valueA, valueB);
    }


}
