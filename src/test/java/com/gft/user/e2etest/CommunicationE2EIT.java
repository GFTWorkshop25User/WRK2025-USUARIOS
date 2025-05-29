package com.gft.user.e2etest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gft.user.application.dto.NotificationDto;
import com.gft.user.infrastructure.dto.NotificationResponse;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock({
        @ConfigureWireMock(name = "communications-client", baseUrlProperties = "app.communicationsBackendUrl")
})
class CommunicationE2EIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1/users";
        WireMock.reset();
    }

    @Test
    void should_getNotifications_when_userExists() throws JsonProcessingException {
        UUID userId = UUID.fromString("7d108b78-2d55-48cf-a39a-8b25bc667fe4");
        String userNotificationsUrl = baseUrl + "/" + userId + "/notifications";

        LocalDateTime now = LocalDateTime.now();
        List<NotificationResponse> notifications = List.of(new NotificationResponse(
                UUID.randomUUID(),
                now,
                userId,
                "Message",
                true
        ));

        List<NotificationDto> notificationDtos = List.of(new NotificationDto(
                "Message",
                    now,
                    true
        ));

        stubFor(get(urlEqualTo("/notifications/" + userId))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(notifications))));

        ResponseEntity<String> response = restTemplate.getForEntity(userNotificationsUrl, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(objectMapper.writeValueAsString(notificationDtos));
    }
}
