package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.model.RequestData;
import com.example.demo.persistence.RequestDataDao;
import com.example.demo.service.RateLimiterService;

//import org.junit.jupiter.api.

//@RunWith(SpringRunner.class)
@SpringBootTest
class RateLimiterApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private RateLimiterService rateLimiterService;

//	@MockBean
//	private RequestDataDao requestDataDao;

	// call for multiple requests

//	@Test
//	public void getAllRequestDataTest() {
//		when(requestDataDao.getAll()).thenReturn(Stream.of(new RequestData()).collect(Collectors.toList()));
//		assertEquals(1, rateLimiterService.getAllRequestData().size());
//	}

//	@Test
//	public void isAllowedSuccessTest() {
//		RequestData testData = new RequestData();
//		when(requestDataDao.getRequestCountInWindow(20, testData.getTimestamp())).thenReturn(2);
//		try {
//			assertEquals(CompletableFuture.completedFuture(Boolean.TRUE).get(),
//					rateLimiterService.isAllowed(testData).get());
//
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
	
	@Test
	public void isAllowedSuccessTest() throws Exception {
		for (int i = 0; i < (rateLimiterService.limit - 2); i++)
			assertThat(rateLimiterService.isAllowed(new RequestData()).get()).isEqualTo(true);

		assertThat(rateLimiterService.isAllowed(new RequestData()).get()).isEqualTo(true);
	}
	
//	@Test
//	public void isAllowedFailureTest() throws Exception {
//		Thread.sleep(rateLimiterService.timeWindowInSec*1000);
//		for (int i = 0; i < rateLimiterService.limit; i++)
//			assertThat(rateLimiterService.isAllowed(new RequestData()).get()).isEqualTo(true);
//
//		assertThat(rateLimiterService.isAllowed(new RequestData()).get()).isEqualTo(false);
//	}
}
