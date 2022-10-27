package org.job.interview.roombookingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.ResultMatcher;

public class JacksonResultMappers {
    protected ObjectMapper objectMapper;

    public JacksonResultMappers(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> ResultMatcher compareEquals(T objectToCompare) {
        return result -> {
            String resultAsString = result.getResponse().getContentAsString();
            T mvcResultObject = (T) objectMapper.readValue(resultAsString, objectToCompare.getClass());
            if (!mvcResultObject.equals(objectToCompare)) {
                AssertionErrors.fail(String.format("Comparing response body with expected object %s",
                        objectMapper.writeValueAsString(objectToCompare)));
            }
        };
    }
}
