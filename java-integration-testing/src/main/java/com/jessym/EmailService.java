package com.jessym;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class EmailService {

    private final RestTemplate template;

    EmailService(@Qualifier("emailServiceTemplate") RestTemplate template) {
        this.template = template;
    }

    void sendWelcomeEmail(String email) {
        try {
            var request = new SendEmailRequest("WELCOME", email);
            template.postForEntity("/send", request, Void.class);
        } catch (RestClientException e) {
            log.info("Received 4xx status code response (client error)", e);
            throw e;
        }
    }

    @Data
    @AllArgsConstructor
    public static class SendEmailRequest {

        @JsonProperty("template")
        private String template;

        @JsonProperty("email")
        private String email;

    }

}
