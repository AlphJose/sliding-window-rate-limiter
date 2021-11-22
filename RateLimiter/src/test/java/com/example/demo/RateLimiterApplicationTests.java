package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.RequestData;
import com.example.demo.service.RateLimiterService;

@SpringBootTest
class RateLimiterApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private RateLimiterService rateLimiterService;

	@Test
	public void isAllowedSuccessTest() throws Exception {
		for (int i = 0; i < (rateLimiterService.limit - 2); i++)
			assertThat(rateLimiterService.isAllowed(new RequestData()).get()).isEqualTo(true);

		assertThat(rateLimiterService.isAllowed(new RequestData()).get()).isEqualTo(true);
	}

	@Test
	public void isAllowedFailureTest() throws Exception {
		Thread.sleep(rateLimiterService.timeWindowInSec * 1000);
		for (int i = 0; i < rateLimiterService.limit; i++)
			assertThat(rateLimiterService.isAllowed(new RequestData()).get()).isEqualTo(true);

		assertThat(rateLimiterService.isAllowed(new RequestData()).get()).isEqualTo(false);
	}
}
