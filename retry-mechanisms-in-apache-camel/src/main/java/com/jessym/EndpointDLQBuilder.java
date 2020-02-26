package com.jessym;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.apache.camel.builder.DefaultErrorHandlerBuilder;

import java.io.PrintWriter;
import java.io.StringWriter;

import static com.jessym.EndpointBuilder.queue;

/**
 * For more information about Camel's Dead Letter Channel, please see
 * https://camel.apache.org/manual/latest/dead-letter-channel.html
 */
@Slf4j
public class EndpointDLQBuilder {

    public static DefaultErrorHandlerBuilder dlq(String endpoint) {
        return new DeadLetterChannelBuilder(queue(endpoint).build())
                .useOriginalMessage()
                .maximumRedeliveries(3)
                .redeliveryDelay(1000)
                .useExponentialBackOff()
                .backOffMultiplier(2)
                .log(log)
                .loggingLevel(LoggingLevel.ERROR)
                .logHandled(true)
                .logExhausted(true)
                .onPrepareFailure(EndpointDLQBuilder::attachException);
    }

    private static void attachException(Exchange exchange) {
        var message = exchange.getIn();
        var exception = (Exception) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
        message.setHeader("Exception", getStackTrace(exception));
    }

    @SneakyThrows
    private static String getStackTrace(Exception exception) {
        @Cleanup var sw = new StringWriter();
        @Cleanup var pw = new PrintWriter(sw, true);
        exception.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

}
