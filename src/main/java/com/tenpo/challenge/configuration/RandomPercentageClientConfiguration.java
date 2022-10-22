package com.tenpo.challenge.configuration;

import com.tenpo.challenge.client.RandomPercentageClient;
import com.tenpo.challenge.client.RandomPercentageRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RandomPercentageClientConfiguration {

    @Bean
    public RandomPercentageClient randomPercentageClient(RandomPercentageRestClient randomPercentageRestClient){
        return randomPercentageRestClient;
    }
}
