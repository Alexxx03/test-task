package org.job.interview.roombookingservice.controller;


import org.job.interview.roombookingservice.ContextConfiguration;
import org.job.interview.roombookingservice.JacksonResultMappers;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ContextConfiguration.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
public class BaseRestControllerIntegrationTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected JacksonResultMappers jacksonResultMappers;


}
