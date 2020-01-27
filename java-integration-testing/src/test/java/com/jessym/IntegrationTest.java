package com.jessym;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class IntegrationTest {

    @LocalServerPort
    private int localServerPort;
    private RestTemplate restTemplate;
    private MockServerClient emailService;

    @Autowired
    private CustomerRepository repository;

    @BeforeClass
    public static void beforeAll() {
        Containers.ensureRunning();
    }

    @Before
    public void before() {
        restTemplate = new RestTemplateBuilder()
                .rootUri("http://localhost:" + localServerPort)
                .build();
        emailService = new MockServerClient(Containers.EMAIL_SERVICE.getContainerIpAddress(), Containers.EMAIL_SERVICE.getServerPort());
        emailService.when(HttpRequest.request("/send"))
                .respond(HttpResponse.response().withStatusCode(204));
    }

    @Test
    public void integrationTest() {
        // Given
        var email = "jessy@example.com";
        var name = "Jessy";
        // When
        var request = Map.ofEntries(
                Map.entry("email", email),
                Map.entry("name", name)
        );
        restTemplate.postForEntity("/customers", request, Void.class);
        // Then
        emailService.verify(HttpRequest.request()
                .withPath("/send")
                .withMethod("POST")
                .withHeader(Header.header("Content-Type", "application/json"))
                .withBody(JsonBody.json(new EmailService.SendEmailRequest("WELCOME", email)))
        );
        var customer = repository.findByEmail(email).orElseThrow();
        assertThat(customer.getEmail()).isEqualTo(email);
        assertThat(customer.getName()).isEqualTo(name);
    }

    @After
    public void after() {
        repository.deleteAll();
        emailService.stop();
    }

}
