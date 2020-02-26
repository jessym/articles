package com.jessym;

import org.apache.camel.Exchange;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import static com.jessym.EndpointBuilder.queue;
import static org.apache.camel.model.dataformat.JsonLibrary.Jackson;

@Component
public class CustomerCreatedProducer extends SpringRouteBuilder {

    @Override
    public void configure() {
        from("timer:dummy?period=3000&repeatCount=1")
                .setBody(this::createDummyEvent)
                .marshal().json(Jackson)
                .to(queue("customer.created").build());
    }

    private CustomerCreated createDummyEvent(Exchange exchange) {
        var dummy = new CustomerCreated();
        dummy.setId("cus_123");
        dummy.setName("Jessy");
        dummy.setEmail("jessy@example.com");
        return dummy;
    }

}
