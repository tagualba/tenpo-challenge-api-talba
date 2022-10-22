package com.tenpo.challenge.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfiguration {

    @Value("${percentage-service.read-timeout}")
    private int readTimeout;

    @Value("${percentage-service.connection-timeout}")
    private int connectionTimeout;

    @Bean
    public RestTemplate restTemplate(){
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(connectionTimeout));
        restTemplateBuilder.setReadTimeout(Duration.ofSeconds(readTimeout));

        return  restTemplateBuilder.build();
    }

}
