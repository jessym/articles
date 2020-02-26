package com.jessym;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    /**
     * Alternatively, one can depend on the `camel-activemq-starter` library,
     * and auto-configure the Camel ActiveMQ component through Spring application properties
     *
     * For more information, see
     * https://camel.apache.org/components/latest/activemq-component.html#_spring_boot_auto_configuration
     */
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(
            @Value("${activemq.username}") String username,
            @Value("${activemq.password}") String password,
            @Value("${activemq.broker-url}") String brokerUrl
    ) {
        return new ActiveMQConnectionFactory(username, password, brokerUrl);
    }

}
