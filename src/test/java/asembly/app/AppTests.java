package asembly.app;

import asembly.app.mapping.ChatMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {ChatMapper.class})
@ActiveProfiles("test")
@Configuration
class AppTests {

	@Test
	void contextLoads() {
	}

}
