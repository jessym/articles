package com.jessym;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static java.lang.String.format;

/**
 * For a complete overview of ActiveMQ component options, please see
 * https://camel.apache.org/components/latest/activemq-component.html#_endpoint_options
 */
@Slf4j
@RequiredArgsConstructor
public class EndpointBuilder {

    private final Type type;
    private final String endpoint;
    private Map<String, String> options = new HashMap<>();

    public static EndpointBuilder queue(String queue) {
        return new EndpointBuilder(Type.queue, queue);
    }

    public static EndpointBuilder topic(String topic) {
        return new EndpointBuilder(Type.topic, topic);
    }

    public EndpointBuilder setConcurrentConsumers(int concurrentConsumers) {
        options.put("concurrentConsumers", String.valueOf(concurrentConsumers));
        return this;
    }

    /**
     * Warning: requires the presence of a TransactionManager
     */
    public EndpointBuilder setTransacted() {
        options.put("transacted", "true");
        return this;
    }

    public String build() {
        var queryString = new StringJoiner("&");
        options.forEach((key, value) -> queryString.add(key + "=" + value));
        return format("activemq:%s:%s?%s", type, endpoint, queryString);
    }

    private enum Type {
        queue,
        topic,
    }

}
