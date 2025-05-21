package com.gft.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = WorkshopApplication.class)
@ActiveProfiles("test")
class WorkshopApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testMain() {
		WorkshopApplication.main(new String[] { "--spring.profiles.active=test" });
	}

}
