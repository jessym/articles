package com.jessym;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;

@Configuration
class EmailServiceConfig {

    @Bean("emailServiceTemplate")
    RestTemplate provideRestTemplate(@Value("${services.email.base-url}") String baseURL) {
        var template = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
        template.setUriTemplateHandler(new DefaultUriBuilderFactory(baseURL));
        return template;
    }

}
