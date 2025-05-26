package com.gft.user.e2etest;

import com.gft.user.common.FeatureFlags;
import com.gft.user.domain.event.UserDisabledEvent;
import com.gft.user.domain.model.user.User;
import com.gft.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/data/h2/schema_testing.sql", "/data/h2/data_testing.sql"})
class DisableUserE2EIT {

    @LocalServerPort
    private int port;

    @MockitoBean
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private String baseUrl;

    @Autowired
    private FeatureFlags featureFlags;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
    }

    @Test
    void should_disableUserAndSendNotification_when_userDisabled() {

        if (!featureFlags.isToggleNotifications()) {
            return;
        }

        UUID userId = UUID.fromString("f19069b6-e374-4969-9ad3-47bb556dbf1e");

        ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl + "/api/v1/users/" + userId,
                HttpMethod.DELETE,
                new HttpEntity<>(null),
                Void.class
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        User user = userRepository.getById(userId);
        assertTrue(user.isDisabled());

        UserDisabledEvent userDisabledEvent = new UserDisabledEvent(userId);

        verify(rabbitTemplate, times(1)).convertAndSend(
                "user",
                "disabled",
                userDisabledEvent
        );
    }
}
