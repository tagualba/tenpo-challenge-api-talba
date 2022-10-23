package com.tenpo.challenge.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@EnableScheduling
@EnableCaching
@Configuration
public class CacheConfiguration {


    private static final Logger logger = LoggerFactory.getLogger(CacheConfiguration.class);

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList( new ConcurrentMapCache("percentage-service")));
        return cacheManager;
    }

    @CacheEvict(allEntries = true, value = {"percentage-service"})
    @Scheduled(fixedDelayString = "${percentage-service.cache.ttl}")
    public void reportCacheEvict() {
        logger.info("Clear Cache {}", dateTimeFormatter.format(LocalDateTime.now()));
    }

}
