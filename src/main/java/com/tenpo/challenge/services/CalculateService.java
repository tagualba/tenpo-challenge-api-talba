package com.tenpo.challenge.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.client.RandomPercentageClient;
import com.tenpo.challenge.exceptions.RandomPercentageClientException;
import com.tenpo.challenge.exceptions.ValidationException;
import com.tenpo.challenge.statics.ErrorCode;
import com.tenpo.challenge.statics.Operations;
import com.tenpo.challenge.utils.GlobalsUtil;
import com.tenpo.challenge.utils.JwtUtil;
import com.tenpo.challenge.validators.CalculateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculateService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RandomPercentageClient randomPercentageClient;


    @Autowired
    private JwtUtil jwtUtill;

    public String calculate(Double valueA, Double valueB) throws ValidationException, JsonProcessingException, RandomPercentageClientException {
        CalculateValidator.validateValues(valueA, valueB);
        GlobalsUtil.setRequest(String.format("{valueA: %s, valueB: %s}", valueA, valueB));
        GlobalsUtil.setOperation(Operations.CALCULATE_PERCENTAGE.name());

        Integer percentage = getPercentage();

        Double result = valueA + valueB;
        result  = result + calculatePercentage(percentage, result);
        String response = String.format("{ (%s + %s) + %s%% = %s }", valueA, valueB, percentage, result);

        GlobalsUtil.setResponse(objectMapper.writeValueAsString(response));
        return response;
    }

    private Double calculatePercentage(Integer percentage, Double value){
        return value * (percentage / Double.valueOf(100));
    }

    private Integer getPercentage() throws RandomPercentageClientException {
        try {
            return randomPercentageClient.getPercentageNow();
        }
        catch (Exception e){
            throw new RandomPercentageClientException(ErrorCode.EXTERNAL_CLIENT_ERROR, e.getMessage());
        }
    }

}
