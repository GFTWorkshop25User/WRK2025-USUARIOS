package com.gft.user;

import org.junit.jupiter.api.Disabled;
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
	@Disabled
	void testMain() {
		WorkshopApplication.main(new String[] {});
	}

}
