package com.gft.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class WorkshopApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testMain() {
		WorkshopApplication.main(new String[] {});
	}

}
