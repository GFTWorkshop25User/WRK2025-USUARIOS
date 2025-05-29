package com.gft.user.e2etest;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoyaltyPointsE2EIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    String baseUrl() {
        return "http://localhost:" + port + "/api/v1/users";
    }

    private final UUID userId = UUID.fromString("ff9e84d8-cf7d-4730-a506-b9fb879d1bea");

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @AfterEach
    void resetDatabase() {
        entityManager.clear();
        jdbcTemplate.execute("DROP ALL OBJECTS DELETE FILES");

        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("/data/h2/schema_testing.sql"));
        populator.addScript(new ClassPathResource("/data/h2/data_testing.sql"));
        populator.execute(dataSource);
    }

    @Test
    @DisplayName("Get user loyalty points")
    void should_getLoyaltyPoints_when_userExists() {

        String loyaltyPointsUrl = baseUrl() + "/" + userId + "/loyalty-points";

        ResponseEntity<Integer> response = restTemplate.getForEntity(loyaltyPointsUrl, Integer.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(180, response.getBody());
    }

    @Test
    @DisplayName("Get user loyalty points")
    void should_responseNotFound_when_userDoesNotExist() {
        UUID randomId = UUID.randomUUID();
        String loyaltyPointsUrl = baseUrl() + "/" + randomId + "/loyalty-points";

        ResponseEntity<String> response = restTemplate.getForEntity(loyaltyPointsUrl, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertEquals("User with id " + randomId + " not found", response.getBody());
    }

    @Test
    @DisplayName("Get user loyalty points")
    void should_responseNotFound_when_userDisabled() {
        UUID disabledUserId = UUID.fromString("fe957011-1565-4e7f-9e3c-0e8f7d015ef3");
        String loyaltyPointsUrl = baseUrl() + "/" + disabledUserId + "/loyalty-points";

        ResponseEntity<String> response = restTemplate.getForEntity(loyaltyPointsUrl, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertEquals("User with id " + disabledUserId + " not found", response.getBody());
    }
}
