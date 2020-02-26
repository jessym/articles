package com.jessym;

import lombok.RequiredArgsConstructor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import static com.jessym.EndpointBuilder.queue;
import static com.jessym.EndpointDLQBuilder.dlq;
import static org.apache.camel.model.dataformat.JsonLibrary.Jackson;

@Component
@RequiredArgsConstructor
public class CustomerCreatedSubscriber extends SpringRouteBuilder {

    private final EmailService emailService;

    @Override
    public void configure() {
        errorHandler(dlq("customer.created.dead"));
        from(queue("customer.created").build())
                .unmarshal().json(Jackson, CustomerCreated.class)
                .bean(this);
    }

    public void process(CustomerCreated event) {
        emailService.sendWelcomeEmail(event.getEmail(), event.getName());
    }

}
