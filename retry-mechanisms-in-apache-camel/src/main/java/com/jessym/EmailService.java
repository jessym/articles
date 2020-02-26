package com.jessym;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * A real implementation would integrate to an external service like Mailgun or Sendgrid here
 */
@Slf4j
@Service
public class EmailService {

    public void sendWelcomeEmail(String address, String name) {
        log.info("Attempting to send e-mail; address={}, name={}", address, name);
        throw new IllegalStateException("Service Unavailable; " + LocalDateTime.now());
    }

}
