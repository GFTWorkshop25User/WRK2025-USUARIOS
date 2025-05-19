package com.gft.user.infrastructure.repository.jpa;

import com.gft.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
@Sql(scripts = {"/data/h2/schema_testing.sql", "/data/h2/data_testing.sql"})
class JpaUserEntityRepositoryTest {

    @Autowired
    private JpaUserEntityRepository jpaUserEntityRepository;

    @Test
    void should_returnLoyaltyPoints() {
        UUID uuid = UUID.fromString("7d108b78-2d55-48cf-a39a-8b25bc667fe4");
        int points = jpaUserEntityRepository.findLoyaltyPointsById(uuid);
        assertEquals(150, points);
    }
}
