package com.gft.user.e2etest;

import com.gft.user.application.user.management.dto.ChangePasswordRequest;
import com.gft.user.application.user.management.dto.UserRequest;
import com.gft.user.domain.model.user.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserManagementE2EIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    String baseUrl() {
        return "http://localhost:" + port + "/api/v1/users";
    }

    private static UUID userId;

    @Test
    @DisplayName("Register new user")
    @Order(1)
    void registerUser() {
        UserRequest userRequest = new UserRequest("Mari", "maripili@gft.com", "Mari1234567!");
        ResponseEntity<UUID> responseEntity = restTemplate.postForEntity(baseUrl(), userRequest, UUID.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        userId = responseEntity.getBody();
    }

    @Test
    @DisplayName("Get the user")
    @Order(2)
    void getUser() {
        assertThat(userId).isNotNull();
        String userUrl = baseUrl() + "/" + userId;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(userUrl, String.class);
        User user = restTemplate.getForObject(userUrl, User.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(userId, user.getId().getUuid());
        assertEquals("Mari", user.getName());
        assertEquals("maripili@gft.com", user.getEmail().value());

    }

    @Test
    @DisplayName("Change the user name")
    @Order(3)
    void changeUsername() {
        assertThat(userId).isNotNull();

        String updateNameUrl = baseUrl() + "/" + userId + "/change-name";
        String userUrl = baseUrl() + "/" + userId;
        String newName = "Maria";
        restTemplate.put(updateNameUrl, newName);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(userUrl, String.class);
        User user = restTemplate.getForObject(userUrl, User.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(newName, user.getName());
    }

    @Test
    @DisplayName("Change the user email")
    @Order(4)
    void changeEmail() {
        assertThat(userId).isNotNull();

        String updateEmailUrl = baseUrl() + "/" + userId + "/change-email";
        String userUrl = baseUrl() + "/" + userId;
        String newEmail = "email@email.com";
        restTemplate.put(updateEmailUrl, newEmail);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(userUrl, String.class);
        User user = restTemplate.getForObject(userUrl, User.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(newEmail, user.getEmail().value());
    }

    @Test
    @DisplayName("Change the user password")
    @Order(5)
    void changePassword() {
        assertThat(userId).isNotNull();

        String updatePasswordUrl = baseUrl() + "/" + userId + "/change-password";
        String userUrl = baseUrl() + "/" + userId;
        String oldPassword = "Mari1234567!";
        String newPassword = "Mariano1234!";

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(oldPassword, newPassword);
        restTemplate.put(updatePasswordUrl, changePasswordRequest);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(userUrl, String.class);
        User user = restTemplate.getForObject(userUrl, User.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(user.getPassword().checkPassword(newPassword));
    }

    @Test
    @DisplayName("Change the user address")
    @Order(6)
    void changeAddress() {
        assertThat(userId).isNotNull();

        String updateAddressUrl = baseUrl() + "/" + userId + "/change-address";
        String userUrl = baseUrl() + "/" + userId;

        Map<String, String> body = new HashMap<>();
        body.put("country", "Colombia");
        body.put("zipCode", "46440");
        body.put("city", "Algeciras");
        body.put("street", "La Pau");

        restTemplate.put(updateAddressUrl, body);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(userUrl, String.class);
        User user = restTemplate.getForObject(userUrl, User.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals("Colombia", user.getAddress().country());
        assertEquals("46440", user.getAddress().zipCode());
        assertEquals("Algeciras", user.getAddress().city());
        assertEquals("La Pau", user.getAddress().street());
    }
}
