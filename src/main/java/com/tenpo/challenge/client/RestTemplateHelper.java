package com.tenpo.challenge.client;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class RestTemplateHelper{

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateHelper.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public <T> T getForEntity(Class<T> clazz, String url) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
            return readValue(response, javaType);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.info("No data found {}", url);
            } else {
                logger.info("rest client exception", exception.getMessage());
            }
        }
        return null;
    }


    private <T> T readValue(ResponseEntity<String> response, JavaType javaType) {
        T result = null;
        if (response.getStatusCode() == HttpStatus.OK ||
                response.getStatusCode() == HttpStatus.CREATED) {
            try {
                result = objectMapper.readValue(response.getBody(), javaType);
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        } else {
            logger.info("No data found {}", response.getStatusCode());
        }
        return result;
    }

}