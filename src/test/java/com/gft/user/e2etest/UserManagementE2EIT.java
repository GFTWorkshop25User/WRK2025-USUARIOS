package com.gft.user.e2etest;

import com.gft.user.application.dto.ChangePasswordRequest;
import com.gft.user.application.dto.UserRequest;
import com.gft.user.domain.model.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserManagementE2EIT {

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
    @DisplayName("Register new user")
    void registerUser() {
        UserRequest userRequest = new UserRequest("Mari", "maripili@gft.com", "Mari1234567!");
        ResponseEntity<UUID> responseEntity = restTemplate.postForEntity(baseUrl(), userRequest, UUID.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Get the user")
    void getUser() {
        String userUrl = baseUrl() + "/" + userId;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(userUrl, String.class);
        User user = restTemplate.getForObject(userUrl, User.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(userId, user.getId().getUuid());
        assertEquals("Eve Black", user.getName());
        assertEquals("eve@example.com", user.getEmail().value());

    }

    @Test
    @DisplayName("Change the user name")
    void changeUsername() {

        String updateNameUrl = baseUrl() + "/" + userId + "/change-name";
        String userUrl = baseUrl() + "/" + userId;
        String newName = "Eve White";
        restTemplate.put(updateNameUrl, newName);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(userUrl, String.class);
        User user = restTemplate.getForObject(userUrl, User.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(newName, user.getName());
    }

    @Test
    @DisplayName("Change the user email")
    void changeEmail() {

        String updateEmailUrl = baseUrl() + "/" + userId + "/change-email";
        String userUrl = baseUrl() + "/" + userId;
        String newEmail = "eve@gft.com";
        restTemplate.put(updateEmailUrl, newEmail);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(userUrl, String.class);
        User user = restTemplate.getForObject(userUrl, User.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(newEmail, user.getEmail().value());
    }

    @Test
    @DisplayName("Change the user password")
    void changePassword() {

        String updatePasswordUrl = baseUrl() + "/" + userId + "/change-password";
        String userUrl = baseUrl() + "/" + userId;
        String oldPassword = "Alice1234567!";
        String newPassword = "White1234567!";

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(oldPassword, newPassword);
        restTemplate.put(updatePasswordUrl, changePasswordRequest);

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(userUrl, String.class);
        User user = restTemplate.getForObject(userUrl, User.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(user.getPassword().checkPassword(newPassword));
    }

    @Test
    @DisplayName("Change the user address")
    void changeAddress() {

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
