package com.alexandre.readinglist;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleWebTest {
    @Value("${local.server.port}")
    private int port;
    
    @Test
    public void pageNotFound() {
        assertThrows(AssertionFailedError.class, () -> {
            try {
                RestTemplate rest = new RestTemplate();
                rest.getForObject("http://localhost:" + port + "/bogusPage", String.class);
                fail("Should result in HTTP 404");
            } catch (Exception e) {
                assertEquals(HttpStatus.NOT_FOUND, ((RestClientResponseException) e).getStatusCode());
                throw e;
            }
        });
    }
}
