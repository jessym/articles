package com.jessym;

import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.PostgreSQLContainer;

public class Containers {

    public static PostgreSQLContainer POSTGRES;
    public static MockServerContainer EMAIL_SERVICE;

    public static void ensureRunning() {
        postgresEnsureRunning();
        emailServiceEnsureRunning();
    }

    private static void postgresEnsureRunning() {
        if (POSTGRES == null) {
            POSTGRES = new PostgreSQLContainer<>("postgres:12.1")
                    .withUsername("admin")
                    .withPassword("password")
                    .withDatabaseName("postgres");
        }
        if (!POSTGRES.isRunning()) {
            POSTGRES.start();
        }
        System.setProperty("spring.datasource.url", POSTGRES.getJdbcUrl());
        System.setProperty("spring.datasource.username", "admin");
        System.setProperty("spring.datasource.password", "password");
    }

    private static void emailServiceEnsureRunning() {
        if (EMAIL_SERVICE == null) {
            EMAIL_SERVICE = new MockServerContainer();
        }
        if (!EMAIL_SERVICE.isRunning()) {
            EMAIL_SERVICE.start();
        }
        System.setProperty("services.email.base-url", EMAIL_SERVICE.getEndpoint());
    }

}
