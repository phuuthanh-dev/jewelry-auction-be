package vn.webapp.backend.auction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.webapp.backend.auction.controller.AuthenticationController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuctionApplicationTests {

	@Autowired
	private AuthenticationController authenticationController;

	@Test
	void contextLoads() throws Exception {
		assertThat(authenticationController).isNotNull();
	}

}
