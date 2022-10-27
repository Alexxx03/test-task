package org.job.interview.roombookingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ContextConfiguration {

    @Bean
    public JacksonResultMappers jacksonResultMappers(ObjectMapper objectMapper) {
        return new JacksonResultMappers(objectMapper);
    }
}
