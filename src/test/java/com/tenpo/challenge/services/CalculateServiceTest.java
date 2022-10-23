package com.tenpo.challenge.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.challenge.client.RandomPercentageRestClient;
import com.tenpo.challenge.exceptions.RandomPercentageClientException;
import com.tenpo.challenge.exceptions.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CalculateServiceTest {

    @Mock
    private RandomPercentageRestClient randomPercentageClient;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CalculateService calculateService;

    @Test
    public void calculateOk() throws ValidationException, RandomPercentageClientException, JsonProcessingException {
        Double valueA = 5.0;

        Double valueB = 5.0;

        Integer percentage = 10;

        when(randomPercentageClient.getPercentageNow()).thenReturn(percentage);

        String res = calculateService.calculate(valueA, valueB);

        assertTrue(res.equals("{ (5.0 + 5.0) + 10% = 11.0 }"));
    }

    @Test
    public void calculateErrorClient() throws ValidationException, RandomPercentageClientException, JsonProcessingException {
        Double valueA = 5.0;

        Double valueB = 5.0;

        Integer percentage = 10;

        when(randomPercentageClient.getPercentageNow()).thenThrow(new RuntimeException());

        assertThrows(RandomPercentageClientException.class, () -> calculateService.calculate(valueA, valueB));

    }
}
