package com.gft.user.e2etest;

import com.gft.user.application.dto.UserRequest;
import com.gft.user.domain.model.user.Address;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserManagementE2EUnhappyPathIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    String baseUrl() {
        return "http://localhost:" + port + "/api/v1/users";
    }

    private final UUID userId = UUID.fromString("2146d125-5c0c-4acc-b093-632cbf624274");

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

    @ParameterizedTest
    @MethodSource("userRequestsAndResponses")
    @DisplayName("Register is angry if requests are wrong")
    void should_throwException_when_requestWrong(UserRequest userRequest, HttpStatus expectedStatus) {
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl(), userRequest, String.class);
        assertThat(response.getStatusCode()).isEqualTo(expectedStatus);
    }

    static Stream<Arguments> userRequestsAndResponses() {
        return Stream.of(
                Arguments.of(new UserRequest("Mari", "emailInvalido", "Mari1234567!"), HttpStatus.BAD_REQUEST),
                Arguments.of(new UserRequest("Mari", "mari@gft.com", "PasswordInvalida"), HttpStatus.BAD_REQUEST),
                Arguments.of(new UserRequest("Mari", "eve@example.com", "Mari1234567!"), HttpStatus.CONFLICT)
        );
    }

    @Test
    @DisplayName("Get is angry because user is not registered")
    void should_throwUserNotFound_when_getUserByIdNotFound() {
        String userUrl = baseUrl() + "/" + userId;

        ResponseEntity<String> response = restTemplate.getForEntity(userUrl, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    @DisplayName("Change username is not happy because user is not registered")
    void should_throwUserNotFound_when_changeUsernameUserIsNotRegistered() {
        String updateNameUrl = baseUrl() + "/ff9e84d8-cf7d-4730-a506-b9fb879d1bea/change-name";
        String userUrl = baseUrl() + "/" + userId;
        String newName = "Eve Black";
        restTemplate.put(updateNameUrl, newName);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(userUrl, String.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Change email is not happy because someone already has it")
    void should_throwEmailAlreadyRegistered_when_emailChange() {
        String updateEmailUrl = baseUrl() + "/ff9e84d8-cf7d-4730-a506-b9fb879d1bea/change-email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = "bob@example.com";

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                updateEmailUrl,
                HttpMethod.PUT,
                entity,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @DisplayName("Change password isn't happy because new password is invalid")
    void should_throwIllegalArg_when_passwordNotCorrect() {
        String url = baseUrl() + "/ff9e84d8-cf7d-4730-a506-b9fb879d1bea/change-password";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("oldPassword", "Alice1234567!");
        jsonBody.put("newPassword", "invalid");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertEquals("Password must be at least 12 characters long and contain at least one lowercase letter, one uppercase letter, one digit, and one special character", response.getBody());
    }

    @Test
    @DisplayName("Address is angry when address is null")
    void should_throwIllegalArg_when_addressEmpty() {
        String updateAddressUrl = baseUrl() + "/ff9e84d8-cf7d-4730-a506-b9fb879d1bea/change-address";

        Address address = new Address("","",null, "");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Address> entity = new HttpEntity<>(address, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                updateAddressUrl,
                HttpMethod.PUT,
                entity,
                String.class
        );

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Change email fails when body is null")
    void should_throwBadRequest_when_changeEmailWithNullBody() {
        String updateEmailUrl = baseUrl() + "/" + userId + "/change-email";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                updateEmailUrl,
                HttpMethod.PUT,
                entity,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


}