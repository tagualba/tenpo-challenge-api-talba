package com.tenpo.challenge.client;


import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class RandomPercentageRestClient implements RandomPercentageClient{

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateHelper.class);

    @Value("${percentage-service.url}")
    private String getPercentageUrl;

    @Autowired
    private RestTemplateHelper restTemplateHelper;

    @Override
    @Cacheable("percentage-service")
    @Retry(name = "percentage")
    public Integer getPercentageNow() {
        logger.info("Event: getPercentageNow - Execute");
        return restTemplateHelper.getForEntity(Integer.class, getPercentageUrl);
    }

}
